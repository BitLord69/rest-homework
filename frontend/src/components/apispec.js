export default {
  data: [
    {
      route: '/',
      method: 'GET',
      description: 'Get a list of all Pokemons',
      returns: 
        [
          {
            type: 'String',
            name: 'name',
          },
          {
            type: 'String',
            name: 'url'
           }
        ],
      exampleURL: 'rest/v1/pokemon/'
    }, 
    {
      route: '/:id',
      method: 'GET',
      description: 'Get a unique Pokemon, using an id (integer)',
      returns: 
        [
          {
            type: 'String',
            name: 'name',
          },
          {
            type: 'String',
            name: 'url'
          },
          {
            type: 'LOTS OF STUFF!',
            name: 'more stuff...'
          }
        ],
      exampleURL: 'rest/v1/pokemon/1'
    }, 
  ]
}