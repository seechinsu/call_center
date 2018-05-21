
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/seech/call_center/conf/routes
// @DATE:Sun May 20 18:35:44 EDT 2018

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
  
    // @LINE:18
    def deletePersonInfo: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.PersonController.deletePersonInfo",
      """
        function(id0) {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "people/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:12
    def createPerson: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.PersonController.createPerson",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "people"})
        }
      """
    )
  
  }

  // @LINE:30
  class ReverseAssets(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:30
    def versioned: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Assets.versioned",
      """
        function(file1) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[play.api.mvc.PathBindable[Asset]].javascriptUnbind + """)("file", file1)})
        }
      """
    )
  
  }

  // @LINE:26
  class ReverseApiHelpController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:26
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

  
    // @LINE:20
    def deleteLawEnforcement: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.LawEnforcementController.deleteLawEnforcement",
      """
        function(id0) {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "lea/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:14
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

  // @LINE:8
  class ReverseVehicleController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:22
    def deleteVehicle: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.VehicleController.deleteVehicle",
      """
        function(id0) {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "vehicle/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:16
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

  
    // @LINE:10
    def createCase: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.CaseController.createCase",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "cases"})
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
  
  }

  // @LINE:25
  class ReverseApiDocsController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:25
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
