
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/seech/call_center/conf/routes
// @DATE:Sun May 20 22:58:55 EDT 2018

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
  
    // @LINE:20
    def createPerson(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "people")
    }
  
    // @LINE:35
    def deletePerson(id:reactivemongo.bson.BSONObjectID): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "people/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].unbind("id", id)))
    }
  
  }

  // @LINE:53
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:53
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

  
    // @LINE:41
    def deleteNarrative(id:reactivemongo.bson.BSONObjectID): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "narrative/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].unbind("id", id)))
    }
  
    // @LINE:26
    def createNarrative(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "narrative")
    }
  
    // @LINE:9
    def getAllNarratives(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "narrative")
    }
  
  }

  // @LINE:49
  class ReverseApiHelpController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:49
    def getResources(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "swagger.json")
    }
  
  }

  // @LINE:7
  class ReverseLawEnforcementController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:37
    def deleteLawEnforcement(id:reactivemongo.bson.BSONObjectID): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "lea/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].unbind("id", id)))
    }
  
    // @LINE:22
    def createLawEnforcement(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "lea")
    }
  
    // @LINE:7
    def getAllLea(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "lea")
    }
  
  }

  // @LINE:11
  class ReverseContactInfoController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:45
    def deleteContactInfo(id:reactivemongo.bson.BSONObjectID): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "contact/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].unbind("id", id)))
    }
  
    // @LINE:30
    def createContactInfo(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "contact")
    }
  
    // @LINE:15
    def getContactInfo(id:reactivemongo.bson.BSONObjectID): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "contact/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].unbind("id", id)))
    }
  
    // @LINE:11
    def getAllContactInfo(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "contact")
    }
  
  }

  // @LINE:8
  class ReverseVehicleController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:39
    def deleteVehicle(id:reactivemongo.bson.BSONObjectID): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "vehicle/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].unbind("id", id)))
    }
  
    // @LINE:24
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

  
    // @LINE:18
    def createCase(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "cases")
    }
  
    // @LINE:33
    def deleteCase(id:reactivemongo.bson.BSONObjectID): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "cases/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].unbind("id", id)))
    }
  
    // @LINE:5
    def getAllCases(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "cases")
    }
  
    // @LINE:13
    def getCase(id:reactivemongo.bson.BSONObjectID): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "cases/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].unbind("id", id)))
    }
  
  }

  // @LINE:10
  class ReverseAddressController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:43
    def deleteAddress(id:reactivemongo.bson.BSONObjectID): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "address/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].unbind("id", id)))
    }
  
    // @LINE:14
    def getAddress(id:reactivemongo.bson.BSONObjectID): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "address/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].unbind("id", id)))
    }
  
    // @LINE:10
    def getAllAddresses(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "address")
    }
  
    // @LINE:28
    def createAddress(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "address")
    }
  
  }

  // @LINE:48
  class ReverseApiDocsController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:48
    def redirectToDocs(): Call = {
    
      () match {
      
        // @LINE:48
        case ()  =>
          
          Call("GET", _prefix)
      
      }
    
    }
  
  }


}
