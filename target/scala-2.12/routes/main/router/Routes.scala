
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/seech/call_center/conf/routes
// @DATE:Sun May 20 22:20:20 EDT 2018

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
  LawEnforcementController_3: controllers.LawEnforcementController,
  // @LINE:8
  VehicleController_8: controllers.VehicleController,
  // @LINE:9
  NarrativeController_2: controllers.NarrativeController,
  // @LINE:10
  AddressController_5: controllers.AddressController,
  // @LINE:43
  ApiDocsController_4: controllers.ApiDocsController,
  // @LINE:44
  ApiHelpController_7: controllers.ApiHelpController,
  // @LINE:48
  Assets_6: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:5
    CaseController_0: controllers.CaseController,
    // @LINE:6
    PersonController_1: controllers.PersonController,
    // @LINE:7
    LawEnforcementController_3: controllers.LawEnforcementController,
    // @LINE:8
    VehicleController_8: controllers.VehicleController,
    // @LINE:9
    NarrativeController_2: controllers.NarrativeController,
    // @LINE:10
    AddressController_5: controllers.AddressController,
    // @LINE:43
    ApiDocsController_4: controllers.ApiDocsController,
    // @LINE:44
    ApiHelpController_7: controllers.ApiHelpController,
    // @LINE:48
    Assets_6: controllers.Assets
  ) = this(errorHandler, CaseController_0, PersonController_1, LawEnforcementController_3, VehicleController_8, NarrativeController_2, AddressController_5, ApiDocsController_4, ApiHelpController_7, Assets_6, "/")

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, CaseController_0, PersonController_1, LawEnforcementController_3, VehicleController_8, NarrativeController_2, AddressController_5, ApiDocsController_4, ApiHelpController_7, Assets_6, prefix)
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
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """cases/""" + "$" + """id<[^/]+>""", """controllers.CaseController.getCase(id:reactivemongo.bson.BSONObjectID)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """address/""" + "$" + """id<[^/]+>""", """controllers.AddressController.getAddress(id:reactivemongo.bson.BSONObjectID)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """cases""", """controllers.CaseController.createCase"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """people""", """controllers.PersonController.createPerson"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """lea""", """controllers.LawEnforcementController.createLawEnforcement"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """vehicle""", """controllers.VehicleController.createVehicle"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """narrative""", """controllers.NarrativeController.createNarrative"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """address""", """controllers.AddressController.createAddress"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """cases/""" + "$" + """id<[^/]+>""", """controllers.CaseController.deleteCase(id:reactivemongo.bson.BSONObjectID)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """people/""" + "$" + """id<[^/]+>""", """controllers.PersonController.deletePerson(id:reactivemongo.bson.BSONObjectID)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """lea/""" + "$" + """id<[^/]+>""", """controllers.LawEnforcementController.deleteLawEnforcement(id:reactivemongo.bson.BSONObjectID)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """vehicle/""" + "$" + """id<[^/]+>""", """controllers.VehicleController.deleteVehicle(id:reactivemongo.bson.BSONObjectID)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """narrative/""" + "$" + """id<[^/]+>""", """controllers.NarrativeController.deleteNarrative(id:reactivemongo.bson.BSONObjectID)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """address/""" + "$" + """id<[^/]+>""", """controllers.AddressController.deleteAddress(id:reactivemongo.bson.BSONObjectID)"""),
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
    LawEnforcementController_3.getAllLea,
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
    VehicleController_8.getAllVehicles,
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
    AddressController_5.getAllAddresses,
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

  // @LINE:12
  private[this] lazy val controllers_CaseController_getCase6_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("cases/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_CaseController_getCase6_invoker = createInvoker(
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

  // @LINE:13
  private[this] lazy val controllers_AddressController_getAddress7_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("address/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_AddressController_getAddress7_invoker = createInvoker(
    AddressController_5.getAddress(fakeValue[reactivemongo.bson.BSONObjectID]),
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

  // @LINE:16
  private[this] lazy val controllers_CaseController_createCase8_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("cases")))
  )
  private[this] lazy val controllers_CaseController_createCase8_invoker = createInvoker(
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

  // @LINE:18
  private[this] lazy val controllers_PersonController_createPerson9_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("people")))
  )
  private[this] lazy val controllers_PersonController_createPerson9_invoker = createInvoker(
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

  // @LINE:20
  private[this] lazy val controllers_LawEnforcementController_createLawEnforcement10_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("lea")))
  )
  private[this] lazy val controllers_LawEnforcementController_createLawEnforcement10_invoker = createInvoker(
    LawEnforcementController_3.createLawEnforcement,
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

  // @LINE:22
  private[this] lazy val controllers_VehicleController_createVehicle11_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("vehicle")))
  )
  private[this] lazy val controllers_VehicleController_createVehicle11_invoker = createInvoker(
    VehicleController_8.createVehicle,
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

  // @LINE:24
  private[this] lazy val controllers_NarrativeController_createNarrative12_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("narrative")))
  )
  private[this] lazy val controllers_NarrativeController_createNarrative12_invoker = createInvoker(
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

  // @LINE:26
  private[this] lazy val controllers_AddressController_createAddress13_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("address")))
  )
  private[this] lazy val controllers_AddressController_createAddress13_invoker = createInvoker(
    AddressController_5.createAddress,
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

  // @LINE:29
  private[this] lazy val controllers_CaseController_deleteCase14_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("cases/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_CaseController_deleteCase14_invoker = createInvoker(
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

  // @LINE:31
  private[this] lazy val controllers_PersonController_deletePerson15_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("people/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_PersonController_deletePerson15_invoker = createInvoker(
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

  // @LINE:33
  private[this] lazy val controllers_LawEnforcementController_deleteLawEnforcement16_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("lea/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_LawEnforcementController_deleteLawEnforcement16_invoker = createInvoker(
    LawEnforcementController_3.deleteLawEnforcement(fakeValue[reactivemongo.bson.BSONObjectID]),
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

  // @LINE:35
  private[this] lazy val controllers_VehicleController_deleteVehicle17_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("vehicle/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_VehicleController_deleteVehicle17_invoker = createInvoker(
    VehicleController_8.deleteVehicle(fakeValue[reactivemongo.bson.BSONObjectID]),
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

  // @LINE:37
  private[this] lazy val controllers_NarrativeController_deleteNarrative18_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("narrative/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_NarrativeController_deleteNarrative18_invoker = createInvoker(
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

  // @LINE:39
  private[this] lazy val controllers_AddressController_deleteAddress19_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("address/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_AddressController_deleteAddress19_invoker = createInvoker(
    AddressController_5.deleteAddress(fakeValue[reactivemongo.bson.BSONObjectID]),
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

  // @LINE:43
  private[this] lazy val controllers_ApiDocsController_redirectToDocs20_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_ApiDocsController_redirectToDocs20_invoker = createInvoker(
    ApiDocsController_4.redirectToDocs,
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

  // @LINE:44
  private[this] lazy val controllers_ApiHelpController_getResources21_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("swagger.json")))
  )
  private[this] lazy val controllers_ApiHelpController_getResources21_invoker = createInvoker(
    ApiHelpController_7.getResources,
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

  // @LINE:45
  private[this] lazy val controllers_ApiDocsController_redirectToDocs22_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api-docs")))
  )
  private[this] lazy val controllers_ApiDocsController_redirectToDocs22_invoker = createInvoker(
    ApiDocsController_4.redirectToDocs,
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

  // @LINE:48
  private[this] lazy val controllers_Assets_versioned23_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned23_invoker = createInvoker(
    Assets_6.versioned(fakeValue[String], fakeValue[Asset]),
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
        controllers_LawEnforcementController_getAllLea2_invoker.call(LawEnforcementController_3.getAllLea)
      }
  
    // @LINE:8
    case controllers_VehicleController_getAllVehicles3_route(params@_) =>
      call { 
        controllers_VehicleController_getAllVehicles3_invoker.call(VehicleController_8.getAllVehicles)
      }
  
    // @LINE:9
    case controllers_NarrativeController_getAllNarratives4_route(params@_) =>
      call { 
        controllers_NarrativeController_getAllNarratives4_invoker.call(NarrativeController_2.getAllNarratives)
      }
  
    // @LINE:10
    case controllers_AddressController_getAllAddresses5_route(params@_) =>
      call { 
        controllers_AddressController_getAllAddresses5_invoker.call(AddressController_5.getAllAddresses)
      }
  
    // @LINE:12
    case controllers_CaseController_getCase6_route(params@_) =>
      call(params.fromPath[reactivemongo.bson.BSONObjectID]("id", None)) { (id) =>
        controllers_CaseController_getCase6_invoker.call(CaseController_0.getCase(id))
      }
  
    // @LINE:13
    case controllers_AddressController_getAddress7_route(params@_) =>
      call(params.fromPath[reactivemongo.bson.BSONObjectID]("id", None)) { (id) =>
        controllers_AddressController_getAddress7_invoker.call(AddressController_5.getAddress(id))
      }
  
    // @LINE:16
    case controllers_CaseController_createCase8_route(params@_) =>
      call { 
        controllers_CaseController_createCase8_invoker.call(CaseController_0.createCase)
      }
  
    // @LINE:18
    case controllers_PersonController_createPerson9_route(params@_) =>
      call { 
        controllers_PersonController_createPerson9_invoker.call(PersonController_1.createPerson)
      }
  
    // @LINE:20
    case controllers_LawEnforcementController_createLawEnforcement10_route(params@_) =>
      call { 
        controllers_LawEnforcementController_createLawEnforcement10_invoker.call(LawEnforcementController_3.createLawEnforcement)
      }
  
    // @LINE:22
    case controllers_VehicleController_createVehicle11_route(params@_) =>
      call { 
        controllers_VehicleController_createVehicle11_invoker.call(VehicleController_8.createVehicle)
      }
  
    // @LINE:24
    case controllers_NarrativeController_createNarrative12_route(params@_) =>
      call { 
        controllers_NarrativeController_createNarrative12_invoker.call(NarrativeController_2.createNarrative)
      }
  
    // @LINE:26
    case controllers_AddressController_createAddress13_route(params@_) =>
      call { 
        controllers_AddressController_createAddress13_invoker.call(AddressController_5.createAddress)
      }
  
    // @LINE:29
    case controllers_CaseController_deleteCase14_route(params@_) =>
      call(params.fromPath[reactivemongo.bson.BSONObjectID]("id", None)) { (id) =>
        controllers_CaseController_deleteCase14_invoker.call(CaseController_0.deleteCase(id))
      }
  
    // @LINE:31
    case controllers_PersonController_deletePerson15_route(params@_) =>
      call(params.fromPath[reactivemongo.bson.BSONObjectID]("id", None)) { (id) =>
        controllers_PersonController_deletePerson15_invoker.call(PersonController_1.deletePerson(id))
      }
  
    // @LINE:33
    case controllers_LawEnforcementController_deleteLawEnforcement16_route(params@_) =>
      call(params.fromPath[reactivemongo.bson.BSONObjectID]("id", None)) { (id) =>
        controllers_LawEnforcementController_deleteLawEnforcement16_invoker.call(LawEnforcementController_3.deleteLawEnforcement(id))
      }
  
    // @LINE:35
    case controllers_VehicleController_deleteVehicle17_route(params@_) =>
      call(params.fromPath[reactivemongo.bson.BSONObjectID]("id", None)) { (id) =>
        controllers_VehicleController_deleteVehicle17_invoker.call(VehicleController_8.deleteVehicle(id))
      }
  
    // @LINE:37
    case controllers_NarrativeController_deleteNarrative18_route(params@_) =>
      call(params.fromPath[reactivemongo.bson.BSONObjectID]("id", None)) { (id) =>
        controllers_NarrativeController_deleteNarrative18_invoker.call(NarrativeController_2.deleteNarrative(id))
      }
  
    // @LINE:39
    case controllers_AddressController_deleteAddress19_route(params@_) =>
      call(params.fromPath[reactivemongo.bson.BSONObjectID]("id", None)) { (id) =>
        controllers_AddressController_deleteAddress19_invoker.call(AddressController_5.deleteAddress(id))
      }
  
    // @LINE:43
    case controllers_ApiDocsController_redirectToDocs20_route(params@_) =>
      call { 
        controllers_ApiDocsController_redirectToDocs20_invoker.call(ApiDocsController_4.redirectToDocs)
      }
  
    // @LINE:44
    case controllers_ApiHelpController_getResources21_route(params@_) =>
      call { 
        controllers_ApiHelpController_getResources21_invoker.call(ApiHelpController_7.getResources)
      }
  
    // @LINE:45
    case controllers_ApiDocsController_redirectToDocs22_route(params@_) =>
      call { 
        controllers_ApiDocsController_redirectToDocs22_invoker.call(ApiDocsController_4.redirectToDocs)
      }
  
    // @LINE:48
    case controllers_Assets_versioned23_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned23_invoker.call(Assets_6.versioned(path, file))
      }
  }
}
