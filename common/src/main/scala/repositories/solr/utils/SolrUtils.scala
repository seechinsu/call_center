package repositories.solr.utils

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext.Implicits.global
import io.ino.solrs.AsyncSolrClient
import io.ino.solrs.future.ScalaFutureFactory.Implicit

import org.apache.solr.common.{SolrDocumentList, SolrInputDocument}

import scala.concurrent.Future
import java.beans.{IntrospectionException, Introspector, PropertyDescriptor}
import java.lang.reflect.Modifier
import java.util.Date
import org.apache.solr.client.solrj.response.QueryResponse
import org.apache.solr.client.solrj.SolrQuery
import play.Logger

import scala.collection.mutable
import scala.util.control.Breaks._



object SolrUtils {
  //val logger = Logger

  def autoMapToSolrInputDoc(
    docId: String,
    obj: Object,
    dynamicFieldOverrides: Map[String, String]): SolrInputDocument =
    autoMapToSolrInputDoc("id", docId, obj, dynamicFieldOverrides)

  def autoMapToSolrInputDoc(
    idFieldName: String,
    docId: String,
    obj: Object,
    dynamicFieldOverrides: Map[String, String]): SolrInputDocument = {
    val doc = new SolrInputDocument()
    doc.setField(idFieldName, docId)
    if (obj == null) return doc

    val objClass = obj.getClass
    val fields = new mutable.HashSet[String]()
    val publicFields = objClass.getFields

    if (publicFields != null) {
      breakable {
        for (f <- publicFields) {
          // only non-static public
          if (Modifier.isStatic(f.getModifiers) || !Modifier.isPublic(f.getModifiers))
            break()
          else {
            var value: Option[Object] = None
            try {
              value = Some(f.get(obj))
            } //catch {
              //case e: IllegalAccessException => logger.error("Exception during reflection ", e)
            //}

            if (value.isDefined) {
              val fieldName = f.getName
              fields.add(fieldName)
              val fieldOverride = if (dynamicFieldOverrides != null) dynamicFieldOverrides.get(fieldName) else null
              if (f.getType != null)
                addField(doc, fieldName, value, f.getType, fieldOverride)
            }
          }
        }
      }

    }

    var props: Option[Array[PropertyDescriptor]] = None
    try {
      val info = Introspector.getBeanInfo(objClass)
      props = Some(info.getPropertyDescriptors)
    } //catch {
      //case e: IntrospectionException => logger.warn("Can't get BeanInfo for class: " + objClass)
    //}

    if (props.isDefined) {
      for (pd <- props.get) {
        val propName  = pd.getName
        breakable {
          if ("class".equals(propName) || fields.contains(propName)) break()
          else {
            val readMethod = pd.getReadMethod
            readMethod.setAccessible(true)
            if (readMethod != null) {
              var value: Option[Object] = None
              try {
                value = Some(readMethod.invoke(obj))
              } //catch {
//                case e: Exception => logger.warn("failed to invoke read method for property '" + pd.getName + "' on " +
//                  "object of type '" + objClass.getName + "' due to: " + e)
              //}

              if (value.isDefined) {
                fields.add(propName)
                val propOverride  = if (dynamicFieldOverrides != null) dynamicFieldOverrides.get(propName) else None
                if (pd.getPropertyType != null)
                  addField(doc, propName, value.get, pd.getPropertyType, propOverride)
              }
            }
          }
        }
      }
    }
    doc
  }

  def addField(
    doc: SolrInputDocument,
    fieldName: String,
    value: Object,
    classType: Class[_],
    dynamicFieldSuffix: Option[String]): Unit = {
    if (classType.isArray) return // TODO: Array types not supported yet ...

    if (dynamicFieldSuffix.isDefined) {
      doc.addField(fieldName + dynamicFieldSuffix.get, value)
    } else {
      var suffix = getDefaultDynamicFieldMapping(classType)
      if (suffix.isDefined) {
        // treat strings with multiple terms as text only if using the default!
        if ("_s".equals(suffix.get)) {
          if (value != null) {
            value match {
              case v1: String =>
                if (v1.indexOf(" ") != -1) suffix = Some("_t")
                val key = fieldName + suffix.get
                doc.addField(key, value)
              case v1: Any =>
                val v = String.valueOf(v1)
                if (v.indexOf(" ") != -1) suffix = Some("_t")
                val key = fieldName + suffix.get
                doc.addField(key, value)
            }
          }
        } else {
          val key = fieldName + suffix.get
          doc.addField(key, value)
        }
      }

    }
  }

  def getDefaultDynamicFieldMapping(clazz: Class[_]): Option[String] = {
    if (classOf[String] == clazz) return Some("_s")
    else if ((classOf[java.lang.Long] == clazz) || (classOf[Long] == clazz)) return Some("_l")
    else if ((classOf[java.lang.Integer] == clazz) || (classOf[Int] == clazz)) return Some("_i")
    else if ((classOf[java.lang.Double] == clazz) || (classOf[Double] == clazz)) return Some("_d")
    else if ((classOf[java.lang.Float] == clazz) || (classOf[Float] == clazz)) return Some("_f")
    else if ((classOf[java.lang.Boolean] == clazz) || (classOf[Boolean] == clazz)) return Some("_b")
    else if (classOf[Date] == clazz) return Some("_tdt")
    //logger.debug("failed to map class '" + clazz + "' to a known dynamic type")
    None
  }
}


