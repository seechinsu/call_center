
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/seech/call_center/conf/routes
// @DATE:Sun May 20 04:25:32 EDT 2018

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.modules.reactivemongo.PathBindables._

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:5
  TodoController_3: controllers.TodoController,
  // @LINE:6
  CaseController_0: controllers.CaseController,
  // @LINE:7
  PersonController_1: controllers.PersonController,
  // @LINE:23
  ApiDocsController_2: controllers.ApiDocsController,
  // @LINE:24
  ApiHelpController_5: controllers.ApiHelpController,
  // @LINE:28
  Assets_4: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:5
    TodoController_3: controllers.TodoController,
    // @LINE:6
    CaseController_0: controllers.CaseController,
    // @LINE:7
    PersonController_1: controllers.PersonController,
    // @LINE:23
    ApiDocsController_2: controllers.ApiDocsController,
    // @LINE:24
    ApiHelpController_5: controllers.ApiHelpController,
    // @LINE:28
    Assets_4: controllers.Assets
  ) = this(errorHandler, TodoController_3, CaseController_0, PersonController_1, ApiDocsController_2, ApiHelpController_5, Assets_4, "/")

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, TodoController_3, CaseController_0, PersonController_1, ApiDocsController_2, ApiHelpController_5, Assets_4, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """todos""", """controllers.TodoController.getAllTodos"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """cases""", """controllers.CaseController.getAllCases"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """people""", """controllers.PersonController.getAllPeople"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """todos/""" + "$" + """id<[^/]+>""", """controllers.TodoController.getTodo(id:reactivemongo.bson.BSONObjectID)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """todos""", """controllers.TodoController.createTodo"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """cases""", """controllers.CaseController.createCase"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """people""", """controllers.PersonController.createPerson"""),
    ("""PATCH""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """todos/""" + "$" + """id<[^/]+>""", """controllers.TodoController.updateTodo(id:reactivemongo.bson.BSONObjectID)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """todos/""" + "$" + """id<[^/]+>""", """controllers.TodoController.deleteTodo(id:reactivemongo.bson.BSONObjectID)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """people/""" + "$" + """id<[^/]+>""", """controllers.PersonController.deletePersonInfo(id:reactivemongo.bson.BSONObjectID)"""),
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
  private[this] lazy val controllers_TodoController_getAllTodos0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("todos")))
  )
  private[this] lazy val controllers_TodoController_getAllTodos0_invoker = createInvoker(
    TodoController_3.getAllTodos,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TodoController",
      "getAllTodos",
      Nil,
      "GET",
      this.prefix + """todos""",
      """""",
      Seq()
    )
  )

  // @LINE:6
  private[this] lazy val controllers_CaseController_getAllCases1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("cases")))
  )
  private[this] lazy val controllers_CaseController_getAllCases1_invoker = createInvoker(
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

  // @LINE:7
  private[this] lazy val controllers_PersonController_getAllPeople2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("people")))
  )
  private[this] lazy val controllers_PersonController_getAllPeople2_invoker = createInvoker(
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

  // @LINE:8
  private[this] lazy val controllers_TodoController_getTodo3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("todos/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_TodoController_getTodo3_invoker = createInvoker(
    TodoController_3.getTodo(fakeValue[reactivemongo.bson.BSONObjectID]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TodoController",
      "getTodo",
      Seq(classOf[reactivemongo.bson.BSONObjectID]),
      "GET",
      this.prefix + """todos/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:10
  private[this] lazy val controllers_TodoController_createTodo4_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("todos")))
  )
  private[this] lazy val controllers_TodoController_createTodo4_invoker = createInvoker(
    TodoController_3.createTodo,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TodoController",
      "createTodo",
      Nil,
      "POST",
      this.prefix + """todos""",
      """""",
      Seq("""nocsrf""")
    )
  )

  // @LINE:12
  private[this] lazy val controllers_CaseController_createCase5_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("cases")))
  )
  private[this] lazy val controllers_CaseController_createCase5_invoker = createInvoker(
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

  // @LINE:14
  private[this] lazy val controllers_PersonController_createPerson6_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("people")))
  )
  private[this] lazy val controllers_PersonController_createPerson6_invoker = createInvoker(
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

  // @LINE:16
  private[this] lazy val controllers_TodoController_updateTodo7_route = Route("PATCH",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("todos/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_TodoController_updateTodo7_invoker = createInvoker(
    TodoController_3.updateTodo(fakeValue[reactivemongo.bson.BSONObjectID]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TodoController",
      "updateTodo",
      Seq(classOf[reactivemongo.bson.BSONObjectID]),
      "PATCH",
      this.prefix + """todos/""" + "$" + """id<[^/]+>""",
      """""",
      Seq("""nocsrf""")
    )
  )

  // @LINE:18
  private[this] lazy val controllers_TodoController_deleteTodo8_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("todos/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_TodoController_deleteTodo8_invoker = createInvoker(
    TodoController_3.deleteTodo(fakeValue[reactivemongo.bson.BSONObjectID]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TodoController",
      "deleteTodo",
      Seq(classOf[reactivemongo.bson.BSONObjectID]),
      "DELETE",
      this.prefix + """todos/""" + "$" + """id<[^/]+>""",
      """""",
      Seq("""nocsrf""")
    )
  )

  // @LINE:20
  private[this] lazy val controllers_PersonController_deletePersonInfo9_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("people/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_PersonController_deletePersonInfo9_invoker = createInvoker(
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

  // @LINE:23
  private[this] lazy val controllers_ApiDocsController_redirectToDocs10_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_ApiDocsController_redirectToDocs10_invoker = createInvoker(
    ApiDocsController_2.redirectToDocs,
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

  // @LINE:24
  private[this] lazy val controllers_ApiHelpController_getResources11_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("swagger.json")))
  )
  private[this] lazy val controllers_ApiHelpController_getResources11_invoker = createInvoker(
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

  // @LINE:25
  private[this] lazy val controllers_ApiDocsController_redirectToDocs12_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api-docs")))
  )
  private[this] lazy val controllers_ApiDocsController_redirectToDocs12_invoker = createInvoker(
    ApiDocsController_2.redirectToDocs,
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

  // @LINE:28
  private[this] lazy val controllers_Assets_versioned13_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned13_invoker = createInvoker(
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
    case controllers_TodoController_getAllTodos0_route(params@_) =>
      call { 
        controllers_TodoController_getAllTodos0_invoker.call(TodoController_3.getAllTodos)
      }
  
    // @LINE:6
    case controllers_CaseController_getAllCases1_route(params@_) =>
      call { 
        controllers_CaseController_getAllCases1_invoker.call(CaseController_0.getAllCases)
      }
  
    // @LINE:7
    case controllers_PersonController_getAllPeople2_route(params@_) =>
      call { 
        controllers_PersonController_getAllPeople2_invoker.call(PersonController_1.getAllPeople)
      }
  
    // @LINE:8
    case controllers_TodoController_getTodo3_route(params@_) =>
      call(params.fromPath[reactivemongo.bson.BSONObjectID]("id", None)) { (id) =>
        controllers_TodoController_getTodo3_invoker.call(TodoController_3.getTodo(id))
      }
  
    // @LINE:10
    case controllers_TodoController_createTodo4_route(params@_) =>
      call { 
        controllers_TodoController_createTodo4_invoker.call(TodoController_3.createTodo)
      }
  
    // @LINE:12
    case controllers_CaseController_createCase5_route(params@_) =>
      call { 
        controllers_CaseController_createCase5_invoker.call(CaseController_0.createCase)
      }
  
    // @LINE:14
    case controllers_PersonController_createPerson6_route(params@_) =>
      call { 
        controllers_PersonController_createPerson6_invoker.call(PersonController_1.createPerson)
      }
  
    // @LINE:16
    case controllers_TodoController_updateTodo7_route(params@_) =>
      call(params.fromPath[reactivemongo.bson.BSONObjectID]("id", None)) { (id) =>
        controllers_TodoController_updateTodo7_invoker.call(TodoController_3.updateTodo(id))
      }
  
    // @LINE:18
    case controllers_TodoController_deleteTodo8_route(params@_) =>
      call(params.fromPath[reactivemongo.bson.BSONObjectID]("id", None)) { (id) =>
        controllers_TodoController_deleteTodo8_invoker.call(TodoController_3.deleteTodo(id))
      }
  
    // @LINE:20
    case controllers_PersonController_deletePersonInfo9_route(params@_) =>
      call(params.fromPath[reactivemongo.bson.BSONObjectID]("id", None)) { (id) =>
        controllers_PersonController_deletePersonInfo9_invoker.call(PersonController_1.deletePersonInfo(id))
      }
  
    // @LINE:23
    case controllers_ApiDocsController_redirectToDocs10_route(params@_) =>
      call { 
        controllers_ApiDocsController_redirectToDocs10_invoker.call(ApiDocsController_2.redirectToDocs)
      }
  
    // @LINE:24
    case controllers_ApiHelpController_getResources11_route(params@_) =>
      call { 
        controllers_ApiHelpController_getResources11_invoker.call(ApiHelpController_5.getResources)
      }
  
    // @LINE:25
    case controllers_ApiDocsController_redirectToDocs12_route(params@_) =>
      call { 
        controllers_ApiDocsController_redirectToDocs12_invoker.call(ApiDocsController_2.redirectToDocs)
      }
  
    // @LINE:28
    case controllers_Assets_versioned13_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned13_invoker.call(Assets_4.versioned(path, file))
      }
  }
}
