package mobiletest.vowei.com.uiautomatorsendmessage;

import org.junit.runners.*;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/**
 * Created by shiyimin on 01/07/2017.
 */

public class DynamicSuite extends Suite {
    public DynamicSuite(Class<?> klass, RunnerBuilder builder) throws InitializationError {
        super(builder, klass, DynamicSuiteBuilder.createRepeatSuite());
    }
}
