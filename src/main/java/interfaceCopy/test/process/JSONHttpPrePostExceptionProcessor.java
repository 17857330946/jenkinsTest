package interfaceCopy.test.process;

public class JSONHttpPrePostExceptionProcessor extends JSONHttpRequestProcessor {
    protected IHttpPrePostExceptionCallback rulePrePostCallBack;

    public JSONHttpPrePostExceptionProcessor(IHttpPrePostExceptionCallback rulePrePostCallBack) {
        this.rulePrePostCallBack = rulePrePostCallBack;
    }
}
