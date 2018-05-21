
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/seech/call_center/conf/routes
// @DATE:Sun May 20 22:58:55 EDT 2018

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
  LawEnforcementController_4: controllers.LawEnforcementController,
  // @LINE:8
  VehicleController_9: controllers.VehicleController,
  // @LINE:9
  NarrativeController_2: controllers.NarrativeController,
  // @LINE:10
  AddressController_6: controllers.AddressController,
  // @LINE:11
  ContactInfoController_3: controllers.ContactInfoController,
  // @LINE:48
  ApiDocsController_5: controllers.ApiDocsController,
  // @LINE:49
  ApiHelpController_8: controllers.ApiHelpController,
  // @LINE:53
  Assets_7: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:5
    CaseController_0: controllers.CaseController,
    // @LINE:6
    PersonController_1: controllers.PersonController,
    // @LINE:7
    LawEnforcementController_4: controllers.LawEnforcementController,
    // @LINE:8
    VehicleController_9: controllers.VehicleController,
    // @LINE:9
    NarrativeController_2: controllers.NarrativeController,
    // @LINE:10
    AddressController_6: controllers.AddressController,
    // @LINE:11
    ContactInfoController_3: controllers.ContactInfoController,
    // @LINE:48
    ApiDocsController_5: controllers.ApiDocsController,
    // @LINE:49
    ApiHelpController_8: controllers.ApiHelpController,
    // @LINE:53
    Assets_7: controllers.Assets
  ) = this(errorHandler, CaseController_0, PersonController_1, LawEnforcementController_4, VehicleController_9, NarrativeController_2, AddressController_6, ContactInfoController_3, ApiDocsController_5, ApiHelpController_8, Assets_7, "/")

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, CaseController_0, PersonController_1, LawEnforcementController_4, VehicleController_9, NarrativeController_2, AddressController_6, ContactInfoController_3, ApiDocsController_5, ApiHelpController_8, Assets_7, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """cases""", """controllers.CaseController.getAllCases"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """people""", """controllers.PersonController.getAllPeople"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """lea""", """controllers.LawEnforcementController.getAllLea"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """vehicle""", """controllers.VehicleController.getAllVehicles"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """narrative""", """controllers.NarrativeController.getAllNarratives"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """address""", """controllers.AddressController.getAllAddresses"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """contact""", """controllers.ContactInfoController.getAllContactInfo"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """cases/""" + "$" + """id<[^/]+>""", """controllers.CaseController.getCase(id:reactivemongo.bson.BSONObjectID)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """address/""" + "$" + """id<[^/]+>""", """controllers.AddressController.getAddress(id:reactivemongo.bson.BSONObjectID)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """contact/""" + "$" + """id<[^/]+>""", """controllers.ContactInfoController.getContactInfo(id:reactivemongo.bson.BSONObjectID)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """cases""", """controllers.CaseController.createCase"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """people""", """controllers.PersonController.createPerson"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """lea""", """controllers.LawEnforcementController.createLawEnforcement"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """vehicle""", """controllers.VehicleController.createVehicle"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """narrative""", """controllers.NarrativeController.createNarrative"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """address""", """controllers.AddressController.createAddress"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """contact""", """controllers.ContactInfoController.createContactInfo"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """cases/""" + "$" + """id<[^/]+>""", """controllers.CaseController.deleteCase(id:reactivemongo.bson.BSONObjectID)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """people/""" + "$" + """id<[^/]+>""", """controllers.PersonController.deletePerson(id:reactivemongo.bson.BSONObjectID)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """lea/""" + "$" + """id<[^/]+>""", """controllers.LawEnforcementController.deleteLawEnforcement(id:reactivemongo.bson.BSONObjectID)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """vehicle/""" + "$" + """id<[^/]+>""", """controllers.VehicleController.deleteVehicle(id:reactivemongo.bson.BSONObjectID)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """narrative/""" + "$" + """id<[^/]+>""", """controllers.NarrativeController.deleteNarrative(id:reactivemongo.bson.BSONObjectID)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """address/""" + "$" + """id<[^/]+>""", """controllers.AddressController.deleteAddress(id:reactivemongo.bson.BSONObjectID)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """contact/""" + "$" + """id<[^/]+>""", """controllers.ContactInfoController.deleteContactInfo(id:reactivemongo.bson.BSONObjectID)"""),
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
    LawEnforcementController_4.getAllLea,
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
    VehicleController_9.getAllVehicles,
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

  // @LINE:9
  private[this] lazy val controllers_NarrativeController_getAllNarratives4_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("narrative")))
  )
  private[this] lazy val controllers_NarrativeController_getAllNarratives4_invoker = createInvoker(
    NarrativeController_2.getAllNarratives,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.NarrativeController",
      "getAllNarratives",
      Nil,
      "GET",
      this.prefix + """narrative""",
      """""",
      Seq()
    )
  )

  // @LINE:10
  private[this] lazy val controllers_AddressController_getAllAddresses5_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("address")))
  )
  private[this] lazy val controllers_AddressController_getAllAddresses5_invoker = createInvoker(
    AddressController_6.getAllAddresses,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.AddressController",
      "getAllAddresses",
      Nil,
      "GET",
      this.prefix + """address""",
      """""",
      Seq()
    )
  )

  // @LINE:11
  private[this] lazy val controllers_ContactInfoController_getAllContactInfo6_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("contact")))
  )
  private[this] lazy val controllers_ContactInfoController_getAllContactInfo6_invoker = createInvoker(
    ContactInfoController_3.getAllContactInfo,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ContactInfoController",
      "getAllContactInfo",
      Nil,
      "GET",
      this.prefix + """contact""",
      """""",
      Seq()
    )
  )

  // @LINE:13
  private[this] lazy val controllers_CaseController_getCase7_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("cases/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_CaseController_getCase7_invoker = createInvoker(
    CaseController_0.getCase(fakeValue[reactivemongo.bson.BSONObjectID]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.CaseController",
      "getCase",
      Seq(classOf[reactivemongo.bson.BSONObjectID]),
      "GET",
      this.prefix + """cases/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:14
  private[this] lazy val controllers_AddressController_getAddress8_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("address/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_AddressController_getAddress8_invoker = createInvoker(
    AddressController_6.getAddress(fakeValue[reactivemongo.bson.BSONObjectID]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.AddressController",
      "getAddress",
      Seq(classOf[reactivemongo.bson.BSONObjectID]),
      "GET",
      this.prefix + """address/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:15
  private[this] lazy val controllers_ContactInfoController_getContactInfo9_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("contact/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ContactInfoController_getContactInfo9_invoker = createInvoker(
    ContactInfoController_3.getContactInfo(fakeValue[reactivemongo.bson.BSONObjectID]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ContactInfoController",
      "getContactInfo",
      Seq(classOf[reactivemongo.bson.BSONObjectID]),
      "GET",
      this.prefix + """contact/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:18
  private[this] lazy val controllers_CaseController_createCase10_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("cases")))
  )
  private[this] lazy val controllers_CaseController_createCase10_invoker = createInvoker(
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

  // @LINE:20
  private[this] lazy val controllers_PersonController_createPerson11_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("people")))
  )
  private[this] lazy val controllers_PersonController_createPerson11_invoker = createInvoker(
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

  // @LINE:22
  private[this] lazy val controllers_LawEnforcementController_createLawEnforcement12_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("lea")))
  )
  private[this] lazy val controllers_LawEnforcementController_createLawEnforcement12_invoker = createInvoker(
    LawEnforcementController_4.createLawEnforcement,
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

  // @LINE:24
  private[this] lazy val controllers_VehicleController_createVehicle13_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("vehicle")))
  )
  private[this] lazy val controllers_VehicleController_createVehicle13_invoker = createInvoker(
    VehicleController_9.createVehicle,
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

  // @LINE:26
  private[this] lazy val controllers_NarrativeController_createNarrative14_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("narrative")))
  )
  private[this] lazy val controllers_NarrativeController_createNarrative14_invoker = createInvoker(
    NarrativeController_2.createNarrative,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.NarrativeController",
      "createNarrative",
      Nil,
      "POST",
      this.prefix + """narrative""",
      """""",
      Seq("""nocsrf""")
    )
  )

  // @LINE:28
  private[this] lazy val controllers_AddressController_createAddress15_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("address")))
  )
  private[this] lazy val controllers_AddressController_createAddress15_invoker = createInvoker(
    AddressController_6.createAddress,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.AddressController",
      "createAddress",
      Nil,
      "POST",
      this.prefix + """address""",
      """""",
      Seq("""nocsrf""")
    )
  )

  // @LINE:30
  private[this] lazy val controllers_ContactInfoController_createContactInfo16_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("contact")))
  )
  private[this] lazy val controllers_ContactInfoController_createContactInfo16_invoker = createInvoker(
    ContactInfoController_3.createContactInfo,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ContactInfoController",
      "createContactInfo",
      Nil,
      "POST",
      this.prefix + """contact""",
      """""",
      Seq("""nocsrf""")
    )
  )

  // @LINE:33
  private[this] lazy val controllers_CaseController_deleteCase17_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("cases/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_CaseController_deleteCase17_invoker = createInvoker(
    CaseController_0.deleteCase(fakeValue[reactivemongo.bson.BSONObjectID]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.CaseController",
      "deleteCase",
      Seq(classOf[reactivemongo.bson.BSONObjectID]),
      "DELETE",
      this.prefix + """cases/""" + "$" + """id<[^/]+>""",
      """""",
      Seq("""nocsrf""")
    )
  )

  // @LINE:35
  private[this] lazy val controllers_PersonController_deletePerson18_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("people/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_PersonController_deletePerson18_invoker = createInvoker(
    PersonController_1.deletePerson(fakeValue[reactivemongo.bson.BSONObjectID]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.PersonController",
      "deletePerson",
      Seq(classOf[reactivemongo.bson.BSONObjectID]),
      "DELETE",
      this.prefix + """people/""" + "$" + """id<[^/]+>""",
      """""",
      Seq("""nocsrf""")
    )
  )

  // @LINE:37
  private[this] lazy val controllers_LawEnforcementController_deleteLawEnforcement19_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("lea/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_LawEnforcementController_deleteLawEnforcement19_invoker = createInvoker(
    LawEnforcementController_4.deleteLawEnforcement(fakeValue[reactivemongo.bson.BSONObjectID]),
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

  // @LINE:39
  private[this] lazy val controllers_VehicleController_deleteVehicle20_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("vehicle/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_VehicleController_deleteVehicle20_invoker = createInvoker(
    VehicleController_9.deleteVehicle(fakeValue[reactivemongo.bson.BSONObjectID]),
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

  // @LINE:41
  private[this] lazy val controllers_NarrativeController_deleteNarrative21_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("narrative/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_NarrativeController_deleteNarrative21_invoker = createInvoker(
    NarrativeController_2.deleteNarrative(fakeValue[reactivemongo.bson.BSONObjectID]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.NarrativeController",
      "deleteNarrative",
      Seq(classOf[reactivemongo.bson.BSONObjectID]),
      "DELETE",
      this.prefix + """narrative/""" + "$" + """id<[^/]+>""",
      """""",
      Seq("""nocsrf""")
    )
  )

  // @LINE:43
  private[this] lazy val controllers_AddressController_deleteAddress22_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("address/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_AddressController_deleteAddress22_invoker = createInvoker(
    AddressController_6.deleteAddress(fakeValue[reactivemongo.bson.BSONObjectID]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.AddressController",
      "deleteAddress",
      Seq(classOf[reactivemongo.bson.BSONObjectID]),
      "DELETE",
      this.prefix + """address/""" + "$" + """id<[^/]+>""",
      """""",
      Seq("""nocsrf""")
    )
  )

  // @LINE:45
  private[this] lazy val controllers_ContactInfoController_deleteContactInfo23_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("contact/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ContactInfoController_deleteContactInfo23_invoker = createInvoker(
    ContactInfoController_3.deleteContactInfo(fakeValue[reactivemongo.bson.BSONObjectID]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ContactInfoController",
      "deleteContactInfo",
      Seq(classOf[reactivemongo.bson.BSONObjectID]),
      "DELETE",
      this.prefix + """contact/""" + "$" + """id<[^/]+>""",
      """""",
      Seq("""nocsrf""")
    )
  )

  // @LINE:48
  private[this] lazy val controllers_ApiDocsController_redirectToDocs24_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_ApiDocsController_redirectToDocs24_invoker = createInvoker(
    ApiDocsController_5.redirectToDocs,
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

  // @LINE:49
  private[this] lazy val controllers_ApiHelpController_getResources25_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("swagger.json")))
  )
  private[this] lazy val controllers_ApiHelpController_getResources25_invoker = createInvoker(
    ApiHelpController_8.getResources,
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

  // @LINE:50
  private[this] lazy val controllers_ApiDocsController_redirectToDocs26_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api-docs")))
  )
  private[this] lazy val controllers_ApiDocsController_redirectToDocs26_invoker = createInvoker(
    ApiDocsController_5.redirectToDocs,
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

  // @LINE:53
  private[this] lazy val controllers_Assets_versioned27_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned27_invoker = createInvoker(
    Assets_7.versioned(fakeValue[String], fakeValue[Asset]),
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
        controllers_LawEnforcementController_getAllLea2_invoker.call(LawEnforcementController_4.getAllLea)
      }
  
    // @LINE:8
    case controllers_VehicleController_getAllVehicles3_route(params@_) =>
      call { 
        controllers_VehicleController_getAllVehicles3_invoker.call(VehicleController_9.getAllVehicles)
      }
  
    // @LINE:9
    case controllers_NarrativeController_getAllNarratives4_route(params@_) =>
      call { 
        controllers_NarrativeController_getAllNarratives4_invoker.call(NarrativeController_2.getAllNarratives)
      }
  
    // @LINE:10
    case controllers_AddressController_getAllAddresses5_route(params@_) =>
      call { 
        controllers_AddressController_getAllAddresses5_invoker.call(AddressController_6.getAllAddresses)
      }
  
    // @LINE:11
    case controllers_ContactInfoController_getAllContactInfo6_route(params@_) =>
      call { 
        controllers_ContactInfoController_getAllContactInfo6_invoker.call(ContactInfoController_3.getAllContactInfo)
      }
  
    // @LINE:13
    case controllers_CaseController_getCase7_route(params@_) =>
      call(params.fromPath[reactivemongo.bson.BSONObjectID]("id", None)) { (id) =>
        controllers_CaseController_getCase7_invoker.call(CaseController_0.getCase(id))
      }
  
    // @LINE:14
    case controllers_AddressController_getAddress8_route(params@_) =>
      call(params.fromPath[reactivemongo.bson.BSONObjectID]("id", None)) { (id) =>
        controllers_AddressController_getAddress8_invoker.call(AddressController_6.getAddress(id))
      }
  
    // @LINE:15
    case controllers_ContactInfoController_getContactInfo9_route(params@_) =>
      call(params.fromPath[reactivemongo.bson.BSONObjectID]("id", None)) { (id) =>
        controllers_ContactInfoController_getContactInfo9_invoker.call(ContactInfoController_3.getContactInfo(id))
      }
  
    // @LINE:18
    case controllers_CaseController_createCase10_route(params@_) =>
      call { 
        controllers_CaseController_createCase10_invoker.call(CaseController_0.createCase)
      }
  
    // @LINE:20
    case controllers_PersonController_createPerson11_route(params@_) =>
      call { 
        controllers_PersonController_createPerson11_invoker.call(PersonController_1.createPerson)
      }
  
    // @LINE:22
    case controllers_LawEnforcementController_createLawEnforcement12_route(params@_) =>
      call { 
        controllers_LawEnforcementController_createLawEnforcement12_invoker.call(LawEnforcementController_4.createLawEnforcement)
      }
  
    // @LINE:24
    case controllers_VehicleController_createVehicle13_route(params@_) =>
      call { 
        controllers_VehicleController_createVehicle13_invoker.call(VehicleController_9.createVehicle)
      }
  
    // @LINE:26
    case controllers_NarrativeController_createNarrative14_route(params@_) =>
      call { 
        controllers_NarrativeController_createNarrative14_invoker.call(NarrativeController_2.createNarrative)
      }
  
    // @LINE:28
    case controllers_AddressController_createAddress15_route(params@_) =>
      call { 
        controllers_AddressController_createAddress15_invoker.call(AddressController_6.createAddress)
      }
  
    // @LINE:30
    case controllers_ContactInfoController_createContactInfo16_route(params@_) =>
      call { 
        controllers_ContactInfoController_createContactInfo16_invoker.call(ContactInfoController_3.createContactInfo)
      }
  
    // @LINE:33
    case controllers_CaseController_deleteCase17_route(params@_) =>
      call(params.fromPath[reactivemongo.bson.BSONObjectID]("id", None)) { (id) =>
        controllers_CaseController_deleteCase17_invoker.call(CaseController_0.deleteCase(id))
      }
  
    // @LINE:35
    case controllers_PersonController_deletePerson18_route(params@_) =>
      call(params.fromPath[reactivemongo.bson.BSONObjectID]("id", None)) { (id) =>
        controllers_PersonController_deletePerson18_invoker.call(PersonController_1.deletePerson(id))
      }
  
    // @LINE:37
    case controllers_LawEnforcementController_deleteLawEnforcement19_route(params@_) =>
      call(params.fromPath[reactivemongo.bson.BSONObjectID]("id", None)) { (id) =>
        controllers_LawEnforcementController_deleteLawEnforcement19_invoker.call(LawEnforcementController_4.deleteLawEnforcement(id))
      }
  
    // @LINE:39
    case controllers_VehicleController_deleteVehicle20_route(params@_) =>
      call(params.fromPath[reactivemongo.bson.BSONObjectID]("id", None)) { (id) =>
        controllers_VehicleController_deleteVehicle20_invoker.call(VehicleController_9.deleteVehicle(id))
      }
  
    // @LINE:41
    case controllers_NarrativeController_deleteNarrative21_route(params@_) =>
      call(params.fromPath[reactivemongo.bson.BSONObjectID]("id", None)) { (id) =>
        controllers_NarrativeController_deleteNarrative21_invoker.call(NarrativeController_2.deleteNarrative(id))
      }
  
    // @LINE:43
    case controllers_AddressController_deleteAddress22_route(params@_) =>
      call(params.fromPath[reactivemongo.bson.BSONObjectID]("id", None)) { (id) =>
        controllers_AddressController_deleteAddress22_invoker.call(AddressController_6.deleteAddress(id))
      }
  
    // @LINE:45
    case controllers_ContactInfoController_deleteContactInfo23_route(params@_) =>
      call(params.fromPath[reactivemongo.bson.BSONObjectID]("id", None)) { (id) =>
        controllers_ContactInfoController_deleteContactInfo23_invoker.call(ContactInfoController_3.deleteContactInfo(id))
      }
  
    // @LINE:48
    case controllers_ApiDocsController_redirectToDocs24_route(params@_) =>
      call { 
        controllers_ApiDocsController_redirectToDocs24_invoker.call(ApiDocsController_5.redirectToDocs)
      }
  
    // @LINE:49
    case controllers_ApiHelpController_getResources25_route(params@_) =>
      call { 
        controllers_ApiHelpController_getResources25_invoker.call(ApiHelpController_8.getResources)
      }
  
    // @LINE:50
    case controllers_ApiDocsController_redirectToDocs26_route(params@_) =>
      call { 
        controllers_ApiDocsController_redirectToDocs26_invoker.call(ApiDocsController_5.redirectToDocs)
      }
  
    // @LINE:53
    case controllers_Assets_versioned27_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned27_invoker.call(Assets_7.versioned(path, file))
      }
  }
}
