
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/seech/call_center/conf/routes
// @DATE:Sun May 20 18:35:44 EDT 2018

package controllers;

import router.RoutesPrefix;

public class routes {
  
  public static final controllers.ReversePersonController PersonController = new controllers.ReversePersonController(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseAssets Assets = new controllers.ReverseAssets(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseApiHelpController ApiHelpController = new controllers.ReverseApiHelpController(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseLawEnforcementController LawEnforcementController = new controllers.ReverseLawEnforcementController(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseVehicleController VehicleController = new controllers.ReverseVehicleController(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseCaseController CaseController = new controllers.ReverseCaseController(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseApiDocsController ApiDocsController = new controllers.ReverseApiDocsController(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final controllers.javascript.ReversePersonController PersonController = new controllers.javascript.ReversePersonController(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseAssets Assets = new controllers.javascript.ReverseAssets(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseApiHelpController ApiHelpController = new controllers.javascript.ReverseApiHelpController(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseLawEnforcementController LawEnforcementController = new controllers.javascript.ReverseLawEnforcementController(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseVehicleController VehicleController = new controllers.javascript.ReverseVehicleController(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseCaseController CaseController = new controllers.javascript.ReverseCaseController(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseApiDocsController ApiDocsController = new controllers.javascript.ReverseApiDocsController(RoutesPrefix.byNamePrefix());
  }

}
