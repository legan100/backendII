package gg.pixelgruene.oergpbackend.security;

import gg.pixelgruene.oergpbackend.Main;
import gg.pixelgruene.oergpbackend.user.UserController;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    UserController userController = new UserController();
//TODO: Login schreiben

    public ResponseCookie createCookie(String username){
        //.domain("comgaiming.de")
        String token = generateToken(username);
        return ResponseCookie.from("JWT", token).httpOnly(true).secure(true).maxAge(7200).build();
    }

    public String generateToken(String username){
        UserController user = new UserController();
        Map<String,Object> claims = new HashMap<>();
        claims.put("email", user.getUser().getEmail(username));
        claims.put("role", user.getUser().getGroup().getGroupname().toUpperCase());
        Key key = Keys.hmacShaKeyFor("tokenasdf".getBytes());
        return Jwts.builder().setClaims(claims).setSubject(String.valueOf(userController.getUser().getUserID(username))).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+72000)).signWith(SignatureAlgorithm.HS256, key).compact();
    }

    public Claims exctractClaims(String token){
        try{
            Key key = Keys.hmacShaKeyFor("tokenasdf".getBytes());
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return claims.getBody();
        }catch (Exception e1){
            Main.getLogger().logWarn(e1.toString());
            return null;
        }
    }

}
