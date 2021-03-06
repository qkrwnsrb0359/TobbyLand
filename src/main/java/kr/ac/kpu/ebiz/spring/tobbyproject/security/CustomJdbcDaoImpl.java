package kr.ac.kpu.ebiz.spring.tobbyproject.security;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
 
public class CustomJdbcDaoImpl extends JdbcDaoImpl {

    @Override
    public UserDetails loadUserByUsername(String username)  throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        List<UserDetails> users = loadUsersByUsername(username);

        if (users.size() == 0) {
                logger.debug("Query returned no results for user '" + username + "'");

                UsernameNotFoundException ue = new UsernameNotFoundException(messages.getMessage("JdbcDaoImpl.notFound", new Object[]{username}, "Username {0} not found"));

            System.out.println(ue+"확인");

                throw ue;
            }

        MemberInfo user = (MemberInfo)users.get(0); // contains no GrantedAuthority[]

        Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();

        if (getEnableAuthorities()) {
                dbAuthsSet.addAll(loadUserAuthorities(user.getUsername()));
            }

        if (getEnableGroups()) {
                dbAuthsSet.addAll(loadGroupAuthorities(user.getUsername()));
            }

        List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(dbAuthsSet);
        user.setAuthorities(dbAuths);

        if (dbAuths.size() == 0) {
                logger.debug("User '" + username + "' has no authorities and will be treated as 'not found'");

                UsernameNotFoundException ue = new UsernameNotFoundException(messages.getMessage("JdbcDaoImpl.noAuthority", new Object[] {username}, "User {0} has no GrantedAuthority"));

            System.out.println(ue+"확인");

                throw ue;
            }

    return user;
}

    @Override
    protected List<UserDetails> loadUsersByUsername(String username) {
        // TODO Auto-generated method stub
        return getJdbcTemplate().query(getUsersByUsernameQuery(), new String[] {username}, new RowMapper<UserDetails>() {
            public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                int member_id = rs.getInt(1);
                String user_id = rs.getString(2);
                String password = rs.getString(3);
                boolean enabled = rs.getBoolean(4);
                boolean nonLocked = rs.getBoolean(5);
                String nickname = rs.getString(6);
                return new MemberInfo(member_id, user_id, password, enabled, nonLocked, nickname, AuthorityUtils.NO_AUTHORITIES);
            }

        });
    }

    @Override
    protected List<GrantedAuthority> loadUserAuthorities(String username) {
        // TODO Auto-generated method stub
        return getJdbcTemplate().query(getAuthoritiesByUsernameQuery(), new String[] {username}, new RowMapper<GrantedAuthority>() {
                public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
                        String roleName = getRolePrefix() + rs.getString(1);

                        return new SimpleGrantedAuthority(roleName);
                    }
            });
    }

    @Override
    protected List<GrantedAuthority> loadGroupAuthorities(String username) {
        // TODO Auto-generated method stub
        return super.loadGroupAuthorities(username);
    }
}
