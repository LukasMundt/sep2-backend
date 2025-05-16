package de.haw.se2.speedrun.openapitools.api;

import jakarta.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-16T13:03:18.760533262Z[Etc/UTC]", comments = "Generator version: 7.14.0-SNAPSHOT")
@Controller
@RequestMapping("${openapi.speedrunsOpenAPI30.base-path:}")
public class CategoriesApiController implements CategoriesApi {

    private final NativeWebRequest request;

    @Autowired
    public CategoriesApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

}
