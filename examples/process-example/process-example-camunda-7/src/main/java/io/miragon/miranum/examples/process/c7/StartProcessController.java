package io.miragon.miranum.examples.process.c7;

import io.miragon.miranum.connect.process.application.port.in.StartProcessCommand;
import io.miragon.miranum.connect.process.application.port.out.StartProcessPort;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/process")
@AllArgsConstructor
public class StartProcessController {

    private final StartProcessPort startProcessPort;

    @PutMapping("/start")
    public ResponseEntity<Void> triggerProcessStart(@RequestBody StartProcessRequestDto startProcessRequestDto) {
        startProcessPort.startProcess(new StartProcessCommand(startProcessRequestDto.getProcessKey(), startProcessRequestDto.getVariables()));
        return ResponseEntity.ok().build();
    }
}
