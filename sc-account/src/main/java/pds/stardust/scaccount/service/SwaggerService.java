package pds.stardust.scaccount.service;

import org.springframework.stereotype.Service;
import springfox.documentation.service.ApiInfo;

import java.util.Collections;

/**
 * SwaggerService : Define API info for swagger generation
 */
@Service
public class SwaggerService implements ISwaggerService {

    /**
     * API info
     */
    @Override
    public ApiInfo apiDetails() {
        return new ApiInfo(
                "Account API",
                "API for account",
                "1.0",
                "Test api",
                new springfox.documentation.service.Contact("Stardust Crusaders", "http://pds.stardust", "pds.stardust.crusaders@etu.u-pec.fr"),
                "API License",
                "http://javabrains.io",
                Collections.emptyList());
    }
}