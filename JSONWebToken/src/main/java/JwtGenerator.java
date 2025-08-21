import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtGenerator {

    public static void main(String[] args){
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        String jwt = Jwts.builder()
                .setSubject("Swaraj")
                .setIssuer("MyConsoleApp")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 72000))
                .signWith(key)
                .compact();
        System.out.println();
        System.out.println("JSON WEB TOKEN GENERATED : " + jwt);

        //jwt = jwt.replaceAll("2","M");

        //System.out.println("JSON WEB TOKEN GENERATED TAMPERED : " + jwt);

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        System.out.println();
        System.out.println("Decoded JWT Claims : ");
        System.out.println("Subject : "+claims.getSubject());
        System.out.println("Issuer : "+claims.getIssuer());
        System.out.println("Issued At : "+claims.getIssuedAt());
        System.out.println("Expiration : "+claims.getExpiration());
    }
}
