
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/seech/call_center/conf/routes
// @DATE:Sun May 20 20:37:29 EDT 2018

import play.api.mvc.Call


import _root_.controllers.Assets.Asset
import _root_.play.modules.reactivemongo.PathBindables._

// @LINE:5
package controllers {

  // @LINE:6
  class ReversePersonController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:6
    def getAllPeople(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "people")
    }
  
    // @LINE:14
    def createPerson(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "people")
    }
  
    // @LINE:25
    def deletePerson(id:reactivemongo.bson.BSONObjectID): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "people/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].unbind("id", id)))
    }
  
  }

  // @LINE:40
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:40
    def versioned(file:Asset): Call = {
      implicit lazy val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public"))); _rrc
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[Asset]].unbind("file", file))
    }
  
  }

  // @LINE:9
  class ReverseNarrativeController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:31
    def deleteNarrative(id:reactivemongo.bson.BSONObjectID): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "narrative/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].unbind("id", id)))
    }
  
    // @LINE:20
    def createNarrative(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "narrative")
    }
  
    // @LINE:9
    def getAllNarratives(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "narrative")
    }
  
  }

  // @LINE:36
  class ReverseApiHelpController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:36
    def getResources(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "swagger.json")
    }
  
  }

  // @LINE:7
  class ReverseLawEnforcementController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:27
    def deleteLawEnforcement(id:reactivemongo.bson.BSONObjectID): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "lea/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].unbind("id", id)))
    }
  
    // @LINE:16
    def createLawEnforcement(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "lea")
    }
  
    // @LINE:7
    def getAllLea(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "lea")
    }
  
  }

  // @LINE:8
  class ReverseVehicleController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:29
    def deleteVehicle(id:reactivemongo.bson.BSONObjectID): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "vehicle/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].unbind("id", id)))
    }
  
    // @LINE:18
    def createVehicle(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "vehicle")
    }
  
    // @LINE:8
    def getAllVehicles(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "vehicle")
    }
  
  }

  // @LINE:5
  class ReverseCaseController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:12
    def createCase(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "cases")
    }
  
    // @LINE:23
    def deleteCase(id:reactivemongo.bson.BSONObjectID): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "cases/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].unbind("id", id)))
    }
  
    // @LINE:5
    def getAllCases(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "cases")
    }
  
  }

  // @LINE:35
  class ReverseApiDocsController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:35
    def redirectToDocs(): Call = {
    
      () match {
      
        // @LINE:35
        case ()  =>
          
          Call("GET", _prefix)
      
      }
    
    }
  
  }


}
