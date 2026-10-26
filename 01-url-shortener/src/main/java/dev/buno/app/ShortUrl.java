package dev.buno.app;
import jakarta.persistence.*;
import java.time.Instant;
@Entity
public class ShortUrl {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @Column(nullable=false, unique=true, length=12) public String code;
 @Column(nullable=false, length=2048) public String targetUrl;
 public Instant createdAt = Instant.now();
 protected ShortUrl() {}
 public ShortUrl(String code, String targetUrl) { this.code=code; this.targetUrl=targetUrl; }
}
