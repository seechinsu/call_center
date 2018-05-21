
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/seech/call_center/conf/routes
// @DATE:Sun May 20 20:37:29 EDT 2018

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
  
    // @LINE:14
    def createPerson: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.PersonController.createPerson",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "people"})
        }
      """
    )
  
    // @LINE:25
    def deletePerson: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.PersonController.deletePerson",
      """
        function(id0) {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "people/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
  }

  // @LINE:40
  class ReverseAssets(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:40
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

  
    // @LINE:31
    def deleteNarrative: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.NarrativeController.deleteNarrative",
      """
        function(id0) {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "narrative/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:20
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

  // @LINE:36
  class ReverseApiHelpController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:36
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

  
    // @LINE:27
    def deleteLawEnforcement: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.LawEnforcementController.deleteLawEnforcement",
      """
        function(id0) {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "lea/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:16
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

  
    // @LINE:29
    def deleteVehicle: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.VehicleController.deleteVehicle",
      """
        function(id0) {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "vehicle/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[reactivemongo.bson.BSONObjectID]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:18
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

  
    // @LINE:12
    def createCase: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.CaseController.createCase",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "cases"})
        }
      """
    )
  
    // @LINE:23
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
  
  }

  // @LINE:35
  class ReverseApiDocsController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:35
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
