package com.cinema.minute.Data.Entity;

public enum ERole {
    ROLE_USER{
        public String toString(){
            return "userRole";
        }
    },
    ROLE_MODERATOR{
        public String toString(){
              return "moderateur role";
        }
    },
    ROLE_ADMIN{
        public String toString(){
              return "admin role";
        }
    };

    @Override
    public abstract String toString();
}
