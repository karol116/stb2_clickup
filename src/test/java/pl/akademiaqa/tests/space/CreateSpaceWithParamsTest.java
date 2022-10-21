package pl.akademiaqa.tests.space;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.akademiaqa.dto.space.request.UpdateSpaceRequestDto;
import pl.akademiaqa.request.space.CreateSpaceRequest;
import pl.akademiaqa.request.space.DeleteSpaceRequest;
import pl.akademiaqa.request.space.UpdateSpaceRequest;

import java.util.stream.Stream;

class CreateSpaceWithParamsTest {

    @ParameterizedTest(name = "Create space with space name: {0}")
    @DisplayName("Create space with valid space name")
    @MethodSource({"createSpaceData"})
    void createSpaceTest(String spaceName, String updatedSpaceName) {

        JSONObject space = new JSONObject();
        space.put("name", spaceName);

        Response createResponse = CreateSpaceRequest.createSpace(space);
        Assertions.assertThat(createResponse.statusCode()).isEqualTo(200);
        JsonPath json = createResponse.jsonPath();
        Assertions.assertThat(json.getString("name")).isEqualTo(spaceName);

        String spaceId = json.getString("id");

        UpdateSpaceRequestDto updatedTaskDto = new UpdateSpaceRequestDto();
        updatedTaskDto.setName(updatedSpaceName);
        final var updateResponse = UpdateSpaceRequest.updateSpace(updatedTaskDto, spaceId);
        Assertions.assertThat(updateResponse.getName()).isEqualTo(updatedSpaceName);

        Response deleteResponse = DeleteSpaceRequest.deleteSpace(spaceId);
        Assertions.assertThat(deleteResponse.statusCode()).isEqualTo(200);
    }

    private static Stream<Arguments> createSpaceData() {
        return Stream.of(
                Arguments.of("NAME123", "UNAME321"),
                Arguments.of("123_ 1232", "71-997"),
                Arguments.of("&", "#")
        );
    }
}