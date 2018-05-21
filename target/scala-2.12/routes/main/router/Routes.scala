
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/seech/call_center/conf/routes
// @DATE:Sun May 20 18:35:44 EDT 2018

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.modules.reactivemongo.PathBindables._

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:5
  CaseController_0: controllers.CaseController,
  // @LINE:6
  PersonController_1: controllers.PersonController,
  // @LINE:7
  LawEnforcementController_2: controllers.LawEnforcementController,
  // @LINE:8
  VehicleController_6: controllers.VehicleController,
  // @LINE:25
  ApiDocsController_3: controllers.ApiDocsController,
  // @LINE:26
  ApiHelpController_5: controllers.ApiHelpController,
  // @LINE:30
  Assets_4: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:5
    CaseController_0: controllers.CaseController,
    // @LINE:6
    PersonController_1: controllers.PersonController,
    // @LINE:7
    LawEnforcementController_2: controllers.LawEnforcementController,
    // @LINE:8
    VehicleController_6: controllers.VehicleController,
    // @LINE:25
    ApiDocsController_3: controllers.ApiDocsController,
    // @LINE:26
    ApiHelpController_5: controllers.ApiHelpController,
    // @LINE:30
    Assets_4: controllers.Assets
  ) = this(errorHandler, CaseController_0, PersonController_1, LawEnforcementController_2, VehicleController_6, ApiDocsController_3, ApiHelpController_5, Assets_4, "/")

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, CaseController_0, PersonController_1, LawEnforcementController_2, VehicleController_6, ApiDocsController_3, ApiHelpController_5, Assets_4, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """cases""", """controllers.CaseController.getAllCases"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """people""", """controllers.PersonController.getAllPeople"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """lea""", """controllers.LawEnforcementController.getAllLea"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """vehicle""", """controllers.VehicleController.getAllVehicles"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """cases""", """controllers.CaseController.createCase"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """people""", """controllers.PersonController.createPerson"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """lea""", """controllers.LawEnforcementController.createLawEnforcement"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """vehicle""", """controllers.VehicleController.createVehicle"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """people/""" + "$" + """id<[^/]+>""", """controllers.PersonController.deletePersonInfo(id:reactivemongo.bson.BSONObjectID)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """lea/""" + "$" + """id<[^/]+>""", """controllers.LawEnforcementController.deleteLawEnforcement(id:reactivemongo.bson.BSONObjectID)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """vehicle/""" + "$" + """id<[^/]+>""", """controllers.VehicleController.deleteVehicle(id:reactivemongo.bson.BSONObjectID)"""),
    ("""GET""", this.prefix, """controllers.ApiDocsController.redirectToDocs"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """swagger.json""", """controllers.ApiHelpController.getResources"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """api-docs""", """controllers.ApiDocsController.redirectToDocs"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.versioned(path:String = "/public", file:Asset)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:5
  private[this] lazy val controllers_CaseController_getAllCases0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("cases")))
  )
  private[this] lazy val controllers_CaseController_getAllCases0_invoker = createInvoker(
    CaseController_0.getAllCases,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.CaseController",
      "getAllCases",
      Nil,
      "GET",
      this.prefix + """cases""",
      """""",
      Seq()
    )
  )

  // @LINE:6
  private[this] lazy val controllers_PersonController_getAllPeople1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("people")))
  )
  private[this] lazy val controllers_PersonController_getAllPeople1_invoker = createInvoker(
    PersonController_1.getAllPeople,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.PersonController",
      "getAllPeople",
      Nil,
      "GET",
      this.prefix + """people""",
      """""",
      Seq()
    )
  )

  // @LINE:7
  private[this] lazy val controllers_LawEnforcementController_getAllLea2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("lea")))
  )
  private[this] lazy val controllers_LawEnforcementController_getAllLea2_invoker = createInvoker(
    LawEnforcementController_2.getAllLea,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.LawEnforcementController",
      "getAllLea",
      Nil,
      "GET",
      this.prefix + """lea""",
      """""",
      Seq()
    )
  )

  // @LINE:8
  private[this] lazy val controllers_VehicleController_getAllVehicles3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("vehicle")))
  )
  private[this] lazy val controllers_VehicleController_getAllVehicles3_invoker = createInvoker(
    VehicleController_6.getAllVehicles,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.VehicleController",
      "getAllVehicles",
      Nil,
      "GET",
      this.prefix + """vehicle""",
      """""",
      Seq()
    )
  )

  // @LINE:10
  private[this] lazy val controllers_CaseController_createCase4_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("cases")))
  )
  private[this] lazy val controllers_CaseController_createCase4_invoker = createInvoker(
    CaseController_0.createCase,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.CaseController",
      "createCase",
      Nil,
      "POST",
      this.prefix + """cases""",
      """""",
      Seq("""nocsrf""")
    )
  )

  // @LINE:12
  private[this] lazy val controllers_PersonController_createPerson5_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("people")))
  )
  private[this] lazy val controllers_PersonController_createPerson5_invoker = createInvoker(
    PersonController_1.createPerson,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.PersonController",
      "createPerson",
      Nil,
      "POST",
      this.prefix + """people""",
      """""",
      Seq("""nocsrf""")
    )
  )

  // @LINE:14
  private[this] lazy val controllers_LawEnforcementController_createLawEnforcement6_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("lea")))
  )
  private[this] lazy val controllers_LawEnforcementController_createLawEnforcement6_invoker = createInvoker(
    LawEnforcementController_2.createLawEnforcement,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.LawEnforcementController",
      "createLawEnforcement",
      Nil,
      "POST",
      this.prefix + """lea""",
      """""",
      Seq("""nocsrf""")
    )
  )

  // @LINE:16
  private[this] lazy val controllers_VehicleController_createVehicle7_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("vehicle")))
  )
  private[this] lazy val controllers_VehicleController_createVehicle7_invoker = createInvoker(
    VehicleController_6.createVehicle,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.VehicleController",
      "createVehicle",
      Nil,
      "POST",
      this.prefix + """vehicle""",
      """""",
      Seq("""nocsrf""")
    )
  )

  // @LINE:18
  private[this] lazy val controllers_PersonController_deletePersonInfo8_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("people/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_PersonController_deletePersonInfo8_invoker = createInvoker(
    PersonController_1.deletePersonInfo(fakeValue[reactivemongo.bson.BSONObjectID]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.PersonController",
      "deletePersonInfo",
      Seq(classOf[reactivemongo.bson.BSONObjectID]),
      "DELETE",
      this.prefix + """people/""" + "$" + """id<[^/]+>""",
      """""",
      Seq("""nocsrf""")
    )
  )

  // @LINE:20
  private[this] lazy val controllers_LawEnforcementController_deleteLawEnforcement9_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("lea/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_LawEnforcementController_deleteLawEnforcement9_invoker = createInvoker(
    LawEnforcementController_2.deleteLawEnforcement(fakeValue[reactivemongo.bson.BSONObjectID]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.LawEnforcementController",
      "deleteLawEnforcement",
      Seq(classOf[reactivemongo.bson.BSONObjectID]),
      "DELETE",
      this.prefix + """lea/""" + "$" + """id<[^/]+>""",
      """""",
      Seq("""nocsrf""")
    )
  )

  // @LINE:22
  private[this] lazy val controllers_VehicleController_deleteVehicle10_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("vehicle/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_VehicleController_deleteVehicle10_invoker = createInvoker(
    VehicleController_6.deleteVehicle(fakeValue[reactivemongo.bson.BSONObjectID]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.VehicleController",
      "deleteVehicle",
      Seq(classOf[reactivemongo.bson.BSONObjectID]),
      "DELETE",
      this.prefix + """vehicle/""" + "$" + """id<[^/]+>""",
      """""",
      Seq("""nocsrf""")
    )
  )

  // @LINE:25
  private[this] lazy val controllers_ApiDocsController_redirectToDocs11_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_ApiDocsController_redirectToDocs11_invoker = createInvoker(
    ApiDocsController_3.redirectToDocs,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApiDocsController",
      "redirectToDocs",
      Nil,
      "GET",
      this.prefix + """""",
      """ Swagger docs""",
      Seq()
    )
  )

  // @LINE:26
  private[this] lazy val controllers_ApiHelpController_getResources12_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("swagger.json")))
  )
  private[this] lazy val controllers_ApiHelpController_getResources12_invoker = createInvoker(
    ApiHelpController_5.getResources,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApiHelpController",
      "getResources",
      Nil,
      "GET",
      this.prefix + """swagger.json""",
      """""",
      Seq()
    )
  )

  // @LINE:27
  private[this] lazy val controllers_ApiDocsController_redirectToDocs13_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api-docs")))
  )
  private[this] lazy val controllers_ApiDocsController_redirectToDocs13_invoker = createInvoker(
    ApiDocsController_3.redirectToDocs,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApiDocsController",
      "redirectToDocs",
      Nil,
      "GET",
      this.prefix + """api-docs""",
      """""",
      Seq()
    )
  )

  // @LINE:30
  private[this] lazy val controllers_Assets_versioned14_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned14_invoker = createInvoker(
    Assets_4.versioned(fakeValue[String], fakeValue[Asset]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "versioned",
      Seq(classOf[String], classOf[Asset]),
      "GET",
      this.prefix + """assets/""" + "$" + """file<.+>""",
      """ Map static resources from the /public folder to the /assets URL path""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:5
    case controllers_CaseController_getAllCases0_route(params@_) =>
      call { 
        controllers_CaseController_getAllCases0_invoker.call(CaseController_0.getAllCases)
      }
  
    // @LINE:6
    case controllers_PersonController_getAllPeople1_route(params@_) =>
      call { 
        controllers_PersonController_getAllPeople1_invoker.call(PersonController_1.getAllPeople)
      }
  
    // @LINE:7
    case controllers_LawEnforcementController_getAllLea2_route(params@_) =>
      call { 
        controllers_LawEnforcementController_getAllLea2_invoker.call(LawEnforcementController_2.getAllLea)
      }
  
    // @LINE:8
    case controllers_VehicleController_getAllVehicles3_route(params@_) =>
      call { 
        controllers_VehicleController_getAllVehicles3_invoker.call(VehicleController_6.getAllVehicles)
      }
  
    // @LINE:10
    case controllers_CaseController_createCase4_route(params@_) =>
      call { 
        controllers_CaseController_createCase4_invoker.call(CaseController_0.createCase)
      }
  
    // @LINE:12
    case controllers_PersonController_createPerson5_route(params@_) =>
      call { 
        controllers_PersonController_createPerson5_invoker.call(PersonController_1.createPerson)
      }
  
    // @LINE:14
    case controllers_LawEnforcementController_createLawEnforcement6_route(params@_) =>
      call { 
        controllers_LawEnforcementController_createLawEnforcement6_invoker.call(LawEnforcementController_2.createLawEnforcement)
      }
  
    // @LINE:16
    case controllers_VehicleController_createVehicle7_route(params@_) =>
      call { 
        controllers_VehicleController_createVehicle7_invoker.call(VehicleController_6.createVehicle)
      }
  
    // @LINE:18
    case controllers_PersonController_deletePersonInfo8_route(params@_) =>
      call(params.fromPath[reactivemongo.bson.BSONObjectID]("id", None)) { (id) =>
        controllers_PersonController_deletePersonInfo8_invoker.call(PersonController_1.deletePersonInfo(id))
      }
  
    // @LINE:20
    case controllers_LawEnforcementController_deleteLawEnforcement9_route(params@_) =>
      call(params.fromPath[reactivemongo.bson.BSONObjectID]("id", None)) { (id) =>
        controllers_LawEnforcementController_deleteLawEnforcement9_invoker.call(LawEnforcementController_2.deleteLawEnforcement(id))
      }
  
    // @LINE:22
    case controllers_VehicleController_deleteVehicle10_route(params@_) =>
      call(params.fromPath[reactivemongo.bson.BSONObjectID]("id", None)) { (id) =>
        controllers_VehicleController_deleteVehicle10_invoker.call(VehicleController_6.deleteVehicle(id))
      }
  
    // @LINE:25
    case controllers_ApiDocsController_redirectToDocs11_route(params@_) =>
      call { 
        controllers_ApiDocsController_redirectToDocs11_invoker.call(ApiDocsController_3.redirectToDocs)
      }
  
    // @LINE:26
    case controllers_ApiHelpController_getResources12_route(params@_) =>
      call { 
        controllers_ApiHelpController_getResources12_invoker.call(ApiHelpController_5.getResources)
      }
  
    // @LINE:27
    case controllers_ApiDocsController_redirectToDocs13_route(params@_) =>
      call { 
        controllers_ApiDocsController_redirectToDocs13_invoker.call(ApiDocsController_3.redirectToDocs)
      }
  
    // @LINE:30
    case controllers_Assets_versioned14_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned14_invoker.call(Assets_4.versioned(path, file))
      }
  }
}
