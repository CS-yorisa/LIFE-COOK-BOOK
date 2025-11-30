import { createClient } from '@urql/svelte';
import { dedupExchange, cacheExchange, fetchExchange } from '@urql/core';

export const client = createClient({
    url: 'http://localhost:8080/graphql',
    exchanges: [dedupExchange, cacheExchange, fetchExchange]
});