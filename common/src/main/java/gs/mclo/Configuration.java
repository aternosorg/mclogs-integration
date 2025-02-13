package gs.mclo;

import com.electronwill.nightconfig.core.serde.annotations.SerdeComment;
import com.electronwill.nightconfig.core.serde.annotations.SerdeSkipDeserializingIf;

public class Configuration {
    @SerdeComment(" Base URL for the API used to upload logs")
    @SerdeSkipDeserializingIf({
            SerdeSkipDeserializingIf.SkipDeIf.IS_MISSING,
            SerdeSkipDeserializingIf.SkipDeIf.IS_NULL,
            SerdeSkipDeserializingIf.SkipDeIf.IS_EMPTY
    })
    String apiBaseUrl = "https://api.mclo.gs";

    @SerdeComment(" URL to view logs on the web")
    @SerdeComment(" A trailing slash and the log ID will be appended to this URL")
    @SerdeSkipDeserializingIf({
            SerdeSkipDeserializingIf.SkipDeIf.IS_MISSING,
            SerdeSkipDeserializingIf.SkipDeIf.IS_NULL,
            SerdeSkipDeserializingIf.SkipDeIf.IS_EMPTY
    })
    String viewLogsUrl = "https://mclo.gs";
}
