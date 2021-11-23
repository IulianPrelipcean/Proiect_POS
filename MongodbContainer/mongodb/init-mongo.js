db.createUser(
    {
        user : "root_mongo",
        pwd : "root_mongo",
        roles : [
            {
                role : "userAdminAnyDatabase",
                db : "orders"
            }
        ]
    }
) 
