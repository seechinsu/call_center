
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/seech/call_center/conf/routes
// @DATE:Sun May 20 04:25:32 EDT 2018

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset
import _root_.play.modules.reactivemongo.PathBindables._

// @LINE:5
package controllers.javascript {

  // @LINE:7
  class ReversePersonController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:7
    def getAllPeople: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.PersonController.getAllPeople",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "people"})
        }
      """
    )
  
    // @LINE:20
    def deletePersonInfo: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.PersonController.deletePersonInfo",
      """
        function(id0) {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "people/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:14
    def createPerson: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.PersonController.createPerson",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "people"})
        }
      """
    )
  
  }

  // @LINE:28
  class ReverseAssets(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:28
    def versioned: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Assets.versioned",
      """
        function(file1) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[play.api.mvc.PathBindable[Asset]].javascriptUnbind + """)("file", file1)})
        }
      """
    )
  
  }

  // @LINE:5
  class ReverseTodoController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:8
    def getTodo: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.TodoController.getTodo",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "todos/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:10
    def createTodo: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.TodoController.createTodo",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "todos"})
        }
      """
    )
  
    // @LINE:18
    def deleteTodo: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.TodoController.deleteTodo",
      """
        function(id0) {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "todos/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:16
    def updateTodo: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.TodoController.updateTodo",
      """
        function(id0) {
          return _wA({method:"PATCH", url:"""" + _prefix + { _defaultPrefix } + """" + "todos/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:5
    def getAllTodos: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.TodoController.getAllTodos",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "todos"})
        }
      """
    )
  
  }

  // @LINE:24
  class ReverseApiHelpController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:24
    def getResources: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ApiHelpController.getResources",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "swagger.json"})
        }
      """
    )
  
  }

  // @LINE:6
  class ReverseCaseController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:12
    def createCase: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.CaseController.createCase",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "cases"})
        }
      """
    )
  
    // @LINE:6
    def getAllCases: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.CaseController.getAllCases",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "cases"})
        }
      """
    )
  
  }

  // @LINE:23
  class ReverseApiDocsController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:23
    def redirectToDocs: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ApiDocsController.redirectToDocs",
      """
        function() {
        
          if (true) {
            return _wA({method:"GET", url:"""" + _prefix + """"})
          }
        
        }
      """
    )
  
  }


}
