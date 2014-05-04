
/**
 * SharixServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package webservice.ops;

    /**
     *  SharixServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class SharixServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public SharixServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public SharixServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for unRegisterUser method
            * override this method for handling normal response from unRegisterUser operation
            */
           public void receiveResultunRegisterUser(
                    webservice.ops.SharixServiceStub.UnRegisterUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from unRegisterUser operation
           */
            public void receiveErrorunRegisterUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getConnectedUsers method
            * override this method for handling normal response from getConnectedUsers operation
            */
           public void receiveResultgetConnectedUsers(
        		   webservice.ops.SharixServiceStub.GetConnectedUsersResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getConnectedUsers operation
           */
            public void receiveErrorgetConnectedUsers(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getFilesByUser method
            * override this method for handling normal response from getFilesByUser operation
            */
           public void receiveResultgetFilesByUser(
        		   webservice.ops.SharixServiceStub.GetFilesByUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getFilesByUser operation
           */
            public void receiveErrorgetFilesByUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for registerFilesByUser method
            * override this method for handling normal response from registerFilesByUser operation
            */
           public void receiveResultregisterFilesByUser(
        		   webservice.ops.SharixServiceStub.RegisterFilesByUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from registerFilesByUser operation
           */
            public void receiveErrorregisterFilesByUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for registerNewUser method
            * override this method for handling normal response from registerNewUser operation
            */
           public void receiveResultregisterNewUser(
        		   webservice.ops.SharixServiceStub.RegisterNewUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from registerNewUser operation
           */
            public void receiveErrorregisterNewUser(java.lang.Exception e) {
            }
                


    }
    