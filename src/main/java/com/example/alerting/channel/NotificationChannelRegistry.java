package com.example.alerting.channel;

import com.example.alerting.model.ChannelType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class NotificationChannelRegistry {

    private final Map<ChannelType, NotificationChannel> channels;

    public NotificationChannelRegistry(List<NotificationChannel> channelList) {
        this.channels = channelList.stream()
                .collect(Collectors.toMap(
                        NotificationChannel::getType,
                        Function.identity(),
                        (existing, replacement) -> existing
                ));
    }

    public Optional<NotificationChannel> getChannel(ChannelType type) {
        return Optional.ofNullable(channels.get(type));
    }

    public boolean supportsChannel(ChannelType type) {
        return channels.containsKey(type);
    }
}
