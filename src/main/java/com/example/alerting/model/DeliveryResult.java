package com.example.alerting.model;

public record DeliveryResult(
    ChannelType channelType,
    DeliveryStatus status,
    String errorMessage
) {
    public static DeliveryResult success(ChannelType channelType) {
        return new DeliveryResult(channelType, DeliveryStatus.SUCCESS, null);
    }

    public static DeliveryResult failure(ChannelType channelType, String errorMessage) {
        return new DeliveryResult(channelType, DeliveryStatus.FAILED, errorMessage);
    }
}
