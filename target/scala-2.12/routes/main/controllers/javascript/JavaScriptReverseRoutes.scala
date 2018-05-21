
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/seech/call_center/conf/routes
// @DATE:Sun May 20 22:58:55 EDT 2018

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset
import _root_.play.modules.reactivemongo.PathBindables._

// @LINE:5
package controllers.javascript {

  // @LINE:6
  class ReversePersonController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:6
    def getAllPeople: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.PersonController.getAllPeople",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "people"})
        }
      """
    )
  
    // @LINE:20
    def createPerson: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.PersonController.createPerson",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "people"})
        }
      """
    )
  
    // @LINE:35
    def deletePerson: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.PersonController.deletePerson",
      """
        function(id0) {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "people/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
  }

  // @LINE:53
  class ReverseAssets(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:53
    def versioned: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Assets.versioned",
      """
        function(file1) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[play.api.mvc.PathBindable[Asset]].javascriptUnbind + """)("file", file1)})
        }
      """
    )
  
  }

  // @LINE:9
  class ReverseNarrativeController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:41
    def deleteNarrative: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.NarrativeController.deleteNarrative",
      """
        function(id0) {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "narrative/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:26
    def createNarrative: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.NarrativeController.createNarrative",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "narrative"})
        }
      """
    )
  
    // @LINE:9
    def getAllNarratives: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.NarrativeController.getAllNarratives",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "narrative"})
        }
      """
    )
  
  }

  // @LINE:49
  class ReverseApiHelpController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:49
    def getResources: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ApiHelpController.getResources",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "swagger.json"})
        }
      """
    )
  
  }

  // @LINE:7
  class ReverseLawEnforcementController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:37
    def deleteLawEnforcement: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.LawEnforcementController.deleteLawEnforcement",
      """
        function(id0) {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "lea/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:22
    def createLawEnforcement: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.LawEnforcementController.createLawEnforcement",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "lea"})
        }
      """
    )
  
    // @LINE:7
    def getAllLea: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.LawEnforcementController.getAllLea",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "lea"})
        }
      """
    )
  
  }

  // @LINE:11
  class ReverseContactInfoController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:45
    def deleteContactInfo: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ContactInfoController.deleteContactInfo",
      """
        function(id0) {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "contact/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:30
    def createContactInfo: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ContactInfoController.createContactInfo",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "contact"})
        }
      """
    )
  
    // @LINE:15
    def getContactInfo: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ContactInfoController.getContactInfo",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "contact/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:11
    def getAllContactInfo: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ContactInfoController.getAllContactInfo",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "contact"})
        }
      """
    )
  
  }

  // @LINE:8
  class ReverseVehicleController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:39
    def deleteVehicle: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.VehicleController.deleteVehicle",
      """
        function(id0) {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "vehicle/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:24
    def createVehicle: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.VehicleController.createVehicle",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "vehicle"})
        }
      """
    )
  
    // @LINE:8
    def getAllVehicles: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.VehicleController.getAllVehicles",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "vehicle"})
        }
      """
    )
  
  }

  // @LINE:5
  class ReverseCaseController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:18
    def createCase: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.CaseController.createCase",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "cases"})
        }
      """
    )
  
    // @LINE:33
    def deleteCase: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.CaseController.deleteCase",
      """
        function(id0) {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "cases/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:5
    def getAllCases: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.CaseController.getAllCases",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "cases"})
        }
      """
    )
  
    // @LINE:13
    def getCase: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.CaseController.getCase",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "cases/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
  }

  // @LINE:10
  class ReverseAddressController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:43
    def deleteAddress: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.AddressController.deleteAddress",
      """
        function(id0) {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "address/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:14
    def getAddress: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.AddressController.getAddress",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "address/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:10
    def getAllAddresses: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.AddressController.getAllAddresses",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "address"})
        }
      """
    )
  
    // @LINE:28
    def createAddress: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.AddressController.createAddress",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "address"})
        }
      """
    )
  
  }

  // @LINE:48
  class ReverseApiDocsController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:48
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
