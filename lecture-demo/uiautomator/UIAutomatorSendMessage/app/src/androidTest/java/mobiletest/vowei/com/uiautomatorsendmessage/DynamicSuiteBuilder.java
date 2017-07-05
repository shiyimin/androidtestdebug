package mobiletest.vowei.com.uiautomatorsendmessage;

import org.junit.runner.RunWith;
/**
 * Created by shiyimin on 01/07/2017.
 */
@RunWith(DynamicSuite.class)
public class DynamicSuiteBuilder {
    public static Class[] createRepeatSuite() {
        Class[] ret = new Class[2];
        for (int i = 0; i < ret.length; ++i) {
            ret[i] = ExampleInstrumentedTest.class;
        }

        return ret;
    }
}
