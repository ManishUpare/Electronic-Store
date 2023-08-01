package com.bikkadIT.ElectronicStore.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestHeader = request.getHeader("Authorization");
        // Bearer kdfnjkfne33443rjsnfnjknfv
        log.info("Header : {} ", requestHeader);

        String username = null;
        String token = null;

        if (requestHeader != null && requestHeader.startsWith("Bearer")) {

            //if Header is Valid

            token = requestHeader.substring(7); // Bearer ko cut karke bakiki value lenge

            try {

                username = this.jwtHelper.getUsernameFromToken(token); // token pass karege aur hame username mil jayega

            } catch (IllegalArgumentException e) {
                log.info("Illegal argument while fetching the username !!");

            } catch (ExpiredJwtException e) {
                log.info("Jwt token has expired");

            } catch (MalformedJwtException e) {
                log.info("Some changed has done in token. Invalid token !! ");

            } catch (Exception e) {

                e.printStackTrace();
            }

        } else {
            log.info("Invalid Header Value !!");
        }

        // once we get the token , now validate

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);

            if (validateToken) {

                // sahi chal raha hai to
                // authentication karna h

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(null);

            } else {
                log.info("Validation failed !! ");
            }
        }

        filterChain.doFilter(request, response);

    }
}
