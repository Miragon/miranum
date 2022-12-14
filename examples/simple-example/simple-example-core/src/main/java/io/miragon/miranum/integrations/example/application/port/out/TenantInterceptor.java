package io.miragon.miranum.integrations.example.application.port.out;

import io.miragon.miranum.connect.binder.application.port.out.ExecuteUseCaseInterceptor;
import io.miragon.miranum.connect.binder.domain.WorkerInfo;
import io.miragon.miranum.integrations.example.application.port.in.TenantAwareCommand;
import lombok.extern.java.Log;

@Log
public class TenantInterceptor implements ExecuteUseCaseInterceptor {
    @Override
    public void intercept(final Object data, final WorkerInfo useCaseInfo) {
        if (data instanceof TenantAwareCommand) {
            final TenantAwareCommand command = (TenantAwareCommand) data;
            log.info("set tenant: " + command.getTenant());
        }
    }
}
