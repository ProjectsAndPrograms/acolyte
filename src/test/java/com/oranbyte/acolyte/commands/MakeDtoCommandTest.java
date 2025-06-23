package com.oranbyte.acolyte.commands;

import com.oranbyte.acolyte.AcolyteCommandRoot;
import com.oranbyte.acolyte.constants.AcolyteContext;
import com.oranbyte.acolyte.services.DtoGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MakeDtoCommandTest {

    private MakeDtoCommand dtoCommand;
    private AcolyteCommandRoot root;
    private DtoGeneratorService generatorService;

    @BeforeEach
    void setUp(){
        generatorService = Mockito.mock(DtoGeneratorService.class);
        dtoCommand  = new MakeDtoCommand(){
            {
                this.root = new AcolyteCommandRoot() {
                    {
                        setContext((new AcolyteContext("com.oranbyte.generated")));
                    }
                };
                try {
                    var field = MakeDtoCommand.class.getDeclaredField("generatorService");
                    field.setAccessible(true);
                    field.set(this, generatorService);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    @Test
    void run_ShouldCallGenerateDtoWithCorrectParams() {
        String dtoName = "user";
        dtoCommand.dtoName = dtoName;

        dtoCommand.run();

        Mockito.verify(generatorService)
                .generateDto(dtoName, "com.oranbyte.generated");
    }


}
