
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/seech/call_center/conf/routes
// @DATE:Sun May 20 04:25:32 EDT 2018

import play.api.mvc.Call


import _root_.controllers.Assets.Asset
import _root_.play.modules.reactivemongo.PathBindables._

// @LINE:5
package controllers {

  // @LINE:7
  class ReversePersonController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:7
    def getAllPeople(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "people")
    }
  
    // @LINE:20
    def deletePersonInfo(id:reactivemongo.bson.BSONObjectID): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "people/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].unbind("id", id)))
    }
  
    // @LINE:14
    def createPerson(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "people")
    }
  
  }

  // @LINE:28
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:28
    def versioned(file:Asset): Call = {
      implicit lazy val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public"))); _rrc
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[Asset]].unbind("file", file))
    }
  
  }

  // @LINE:5
  class ReverseTodoController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:8
    def getTodo(id:reactivemongo.bson.BSONObjectID): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "todos/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].unbind("id", id)))
    }
  
    // @LINE:10
    def createTodo(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "todos")
    }
  
    // @LINE:18
    def deleteTodo(id:reactivemongo.bson.BSONObjectID): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "todos/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].unbind("id", id)))
    }
  
    // @LINE:16
    def updateTodo(id:reactivemongo.bson.BSONObjectID): Call = {
      
      Call("PATCH", _prefix + { _defaultPrefix } + "todos/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].unbind("id", id)))
    }
  
    // @LINE:5
    def getAllTodos(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "todos")
    }
  
  }

  // @LINE:24
  class ReverseApiHelpController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:24
    def getResources(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "swagger.json")
    }
  
  }

  // @LINE:6
  class ReverseCaseController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:12
    def createCase(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "cases")
    }
  
    // @LINE:6
    def getAllCases(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "cases")
    }
  
  }

  // @LINE:23
  class ReverseApiDocsController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:23
    def redirectToDocs(): Call = {
    
      () match {
      
        // @LINE:23
        case ()  =>
          
          Call("GET", _prefix)
      
      }
    
    }
  
  }


}
