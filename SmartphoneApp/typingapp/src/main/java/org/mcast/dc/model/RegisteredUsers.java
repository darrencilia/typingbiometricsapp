package org.mcast.dc.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Darren on 04/12/2016.
 */

public class RegisteredUsers
{
    private HashSet<User> entries;
    private HashMap<String, User> byAlias;
    private HashMap<String, User> byEmail;

    public RegisteredUsers() {
        super();
        this.entries = new HashSet<User>();
        this.byAlias = new HashMap<String, User>();
        this.byEmail = new HashMap<String, User>();
    }

    public boolean add(User user) {
        if (user.getAlias() != null) {
            byAlias.put(user.getAlias(), user);
        }

        if (user.getEmail() != null) {
            byEmail.put(user.getEmail(), user);
        }

        return entries.add(user);
    }

    public boolean addAll(Set<User> users) {
        for (User user : users) {
            if (!add(user))
                return false;
        }
        return true;
    }

    public boolean isEmpty(){
        return entries.isEmpty();
    }

    public Set<User> getAll() {
        final Set<User> result = new HashSet<User>();
        result.addAll(entries);
        return result;
    }

    public User getByAlias(String alias) {
        return byAlias.get(alias);
    }

    public User getByEmail(String email) {
        return byEmail.get(email);
    }

    public boolean remove(User user) {
        if (user.getAlias() != null && byAlias.containsKey(user.getAlias()))
            byAlias.remove(user.getAlias());

        if (user.getEmail() != null && byEmail.containsKey(user.getEmail()))
            byEmail.remove(user.getEmail());

        return entries.remove(user);
    }

    public boolean update(User oldEntry, User newEntry) {
        return remove(oldEntry) && add(newEntry);
    }
}