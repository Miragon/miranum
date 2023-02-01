package io.miragon.miranum.integrations.example.application.port.out;

import io.miragon.miranum.connect.worker.application.port.out.WorkerInterceptor;
import io.miragon.miranum.connect.worker.domain.WorkerInfo;
import io.miragon.miranum.integrations.example.application.port.in.TenantAwareCommand;
import lombok.extern.java.Log;

@Log
public class TenantInterceptor implements WorkerInterceptor {
    @Override
    public void intercept(final Object data, final WorkerInfo useCaseInfo) {
        if (data instanceof TenantAwareCommand) {
            final TenantAwareCommand command = (TenantAwareCommand) data;
            log.info("set tenant: " + command.getTenant());
        }
    }
}
