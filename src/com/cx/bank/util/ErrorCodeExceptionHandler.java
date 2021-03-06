


    package com.cx.bank.util;

    import javax.servlet.ServletException;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;

    import org.apache.struts.Globals;
    import org.apache.struts.action.ActionForm;
    import org.apache.struts.action.ActionForward;
    import org.apache.struts.action.ActionMapping;
    import org.apache.struts.action.ActionMessage;
    import org.apache.struts.action.ExceptionHandler;
    import org.apache.struts.config.ExceptionConfig;
    import org.apache.struts.util.ModuleException;
    
    /**
     * <DL><DT><b>功能：</b><DD>银行管理系统的异常处理</DD></DL>
     * 银行管理系统3.0Struts版本
     * @version1.0 2018
     * @author 20152135
     * @param <blooean>
     *
     */

   public class ErrorCodeExceptionHandler extends ExceptionHandler {

    public ActionForward execute(
            Exception ex,
            ExceptionConfig ae,
            ActionMapping mapping,
            ActionForm formInstance,
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException {
    	
    		if (!(ex instanceof ErrorCodeException)) {
    			return super.execute(ex, ae, mapping, formInstance, request, response);
    		}
            ActionForward forward = null;
            ActionMessage error = null;
            String property = null;

            // Build the forward from the exception mapping if it exists
            // or from the form input
            if (ae.getPath() != null) {
                forward = new ActionForward(ae.getPath());
            } else {
                forward = mapping.getInputForward();
            }

            // Figure out the error
            if (ex instanceof ModuleException) {
                error = ((ModuleException) ex).getActionMessage();
                property = ((ModuleException) ex).getProperty();
            } else {
            	ErrorCodeException ece = (ErrorCodeException)ex;
            	String errorCode = ece.getErrorCode();
            	Object[] args = ece.getArgs();
            	error = new ActionMessage(errorCode, args);
            	property = error.getKey();
            	
                //error = new ActionMessage(ae.getKey(), ex.getMessage());
                //property = error.getKey();
            }

            this.logException(ex);

            // Store the exception
            request.setAttribute(Globals.EXCEPTION_KEY, ex);
            this.storeException(request, property, error, forward, ae.getScope());

            return forward;

        }	
}
