package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonPropertyOrder({"app","uri","hits"})
public class ViewStats {
    @JsonProperty("app")
    private String app;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("hits")
    private Long hits;
}
