export default {
  data: [
    {
      route: 'rest/v1/pokemon/',
      method: 'GET',
      description: 'Get a list of all Pokemons',
      returns: 
        [
          {
            type: 'string',
            name: 'name',
          },
          {
            type: 'string',
            name: 'url'
           }
        ],
      exampleURL: 'rest/v1/pokemon/',
      access: 'anonymous',
    }, 
    {
      route: 'rest/v1/pokemon/:id',
      method: 'GET',
      description: 'Fetches a unique Pokemon, using an id',
      parameters: [
          {
            type: 'int',
            name: 'id',
          },
      ],
      returns: 
        [
          {
            name: 'id',
            type: 'int',
          },
          {
            type: 'string',
            name: 'name',
          },
          {
            type: 'string',
            name: 'url'
          },
          {
            type: 'string',
            name: 'ability'
          },
          {
            type: 'string',
            name: 'type'
          },
          {
            type: 'string',
            name: 'imageUrl'
          },
          {
            type: 'int',
            name: 'height',
          },
          {
            type: 'int',
            name: 'weight',
          },

        ],
      exampleURL: 'rest/v1/pokemon/1',
      access: 'ROLE_USER',
    }, 
    {
      route: 'api/auth/signin',
      method: 'POST',
      description: "Let's a user sign in",
      parameters:
        [
          {
            type: 'string',
            name: 'username',
          },
          {
            type: 'string',
            name: 'password',
          }
        ],
      returns: 
        [
          {
            type: 'string',
            name: 'username',
          },
          {
            type: 'array',
            name: 'roles',
            array: [
              {
                name: 'name',
                type: 'string',
                exampleValue: 'ROLE_ADMIN'
              },
            ]
          },
        ],
      access: 'anonymous',
    },
    {
      route: 'api/auth/signup',
      method: 'POST',
      description: "Registers a new user",
      parameters:
        [
          {
            type: 'string',
            name: 'username',
          },
          {
            type: 'string',
            name: 'password',
          }
        ],
      returns: 
        [
          {
            type: 'string',
            name: 'username',
          },
          {
            type: 'array',
            name: 'roles',
            array: [
              {
                name: 'name',
                type: 'string',
                exampleValue: 'ROLE_ADMIN'
              },
            ]
          },
        ],
      access: 'anonymous',
    }, 
    {
      route: 'api/test/all',
      method: 'GET',
      description: "Displays content for all users",
      returns: 
        [
          {
            content: 'string',
          },
        ],
      exampleURL: 'api/test/all',
      access: 'anonymous',
    }, 
    {
      route: 'api/test/user',
      method: 'GET',
      description: "Displays content for authority USER and above",
      returns: 
        [
          {
            type: 'text',
            name: 'content',
          },
        ],
      exampleURL: 'api/test/user',
      access: 'ROLE_USER',
    },
    {
      route: 'api/test/mod',
      method: 'GET',
      description: "Displays content for authority MODERATOR and above",
      returns: 
        [
          {
            type: 'text',
            name: 'content',
          },
        ],
      exampleURL: 'api/test/mod',
      access: 'ROLE_MODERATOR',
    },     
    {
      route: 'api/test/admin',
      method: 'POST',
      description: "Displays content for authority ADMIN",
      returns: 
        [
          {
            type: 'text',
            name: 'content',
          },
        ],
      exampleURL: 'api/test/admin',
      access: 'ROLE_ADMIN',
    },     
  ]
}