package dev.buno.app;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.net.URI;
import java.util.*;
import java.security.SecureRandom;
@RestController @RequestMapping("/api/urls")
public class ShortUrlController {
 private final ShortUrlRepository repo;
 private static final String ALPHABET="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
 private final SecureRandom random=new SecureRandom();
 public ShortUrlController(ShortUrlRepository repo){this.repo=repo;}
 public record CreateUrl(String url) {}
 @PostMapping public ResponseEntity<ShortUrl> create(@RequestBody CreateUrl req) {
  if(req.url()==null || !(req.url().startsWith("http://")||req.url().startsWith("https://")))
   throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"url precisa começar com http:// ou https://");
  String code; do { StringBuilder b=new StringBuilder(); for(int i=0;i<7;i++) b.append(ALPHABET.charAt(random.nextInt(ALPHABET.length()))); code=b.toString(); } while(repo.findByCode(code).isPresent());
  return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(new ShortUrl(code,req.url())));
 }
 @GetMapping public List<ShortUrl> all(){return repo.findAll();}
 @GetMapping("/{id}") public ShortUrl one(@PathVariable Long id){return repo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));}
 @PutMapping("/{id}") public ShortUrl update(@PathVariable Long id,@RequestBody CreateUrl req){
  ShortUrl s=one(id); if(req.url()==null||!(req.url().startsWith("http://")||req.url().startsWith("https://"))) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"URL inválida");
  s.targetUrl=req.url(); return repo.save(s);
 }
 @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){if(!repo.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND); repo.deleteById(id); return ResponseEntity.noContent().build();}
 @GetMapping("/r/{code}") public ResponseEntity<Void> redirect(@PathVariable String code){ShortUrl s=repo.findByCode(code).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND)); return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(s.targetUrl)).build();}
}
