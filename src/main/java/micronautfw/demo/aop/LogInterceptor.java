package micronautfw.demo.aop;

import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.Arrays;

@Singleton
public class LogInterceptor implements MethodInterceptor<Object, Object> {

    private static final Logger LOG = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public Object intercept(MethodInvocationContext<Object, Object> context) {

        if(LOG.isInfoEnabled()) {
            LOG.info(
                String.format(
                    "%n*******************************%nCalling the method %s with args %s%n*******************************",
                    context.getExecutableMethod().getMethodName(),
                    Arrays.toString(context.getParameterValues())
                )
            );
        }
        return context.proceed();
    }
}
