package org.taymaz.mensabot;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import discord4j.core.*;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;


/**
 * Hello world!
 */
public final class App {
  private App() {
  }
  public static String decodeValue(String value) {
    try {
        return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
    } catch (UnsupportedEncodingException ex) {
        throw new RuntimeException(ex.getCause());
    }
}

  /**
   * Says hello to the world.
   * 
   * @param args The arguments of the program.
   */
  public static void main(final String[] args) {
    URLReader url = new URLReader();
    String thisMoment = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneOffset.UTC).format(Instant.now());
    String urlToString = "";
    try {
      urlToString = url.readUrl("https://sls.api.stw-on.de/v1/location/194/menu/"+thisMoment) ;
    } catch (Exception e) {
      e.printStackTrace();
    }
    final String ans = urlToString;
    
    final String token = args[0];
    final DiscordClient client = DiscordClient.create(token);
    final GatewayDiscordClient gateway = client.login().block();

    gateway.on(MessageCreateEvent.class).subscribe(event -> {
      final Message message = event.getMessage();
      if ("!mensa".equals(message.getContent())) {
        final MessageChannel channel = message.getChannel().block();
        channel.createMessage(ans).block();
      }
    });

    gateway.onDisconnect().block();
  }
}
