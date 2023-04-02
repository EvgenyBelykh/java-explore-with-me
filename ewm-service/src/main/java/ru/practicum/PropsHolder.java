package ru.practicum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ewm-service")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropsHolder {

    private String appName;
}
