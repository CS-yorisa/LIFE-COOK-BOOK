<script>
	let { children } = $props();
	import '../app.css';
	import { auth } from '$lib/auth';
	import { goto } from '$app/navigation';
	import { page } from '$app/stores';
	import { onMount } from 'svelte';

	onMount(() => {
		auth.initialize();
	});

	$effect(() => {
		if (!$auth.isLoading) {
			const currentPath = $page.url.pathname;
			const isLoginPage = currentPath === '/member/login';
			const isSignUpPage = currentPath === '/member/signUp';
			const isPublicPage = currentPath === '/' || isLoginPage || isSignUpPage;

			if (!$auth.isAuthenticated && !isPublicPage) {
				goto('/member/login');
			} else if ($auth.isAuthenticated && isLoginPage) {
				goto('/main');
			}
		}
	});
</script>

{#if $auth.isLoading}
	<div class="flex min-h-screen items-center justify-center">
		<div class="h-32 w-32 animate-spin rounded-full border-b-2 border-gray-900"></div>
	</div>
{:else}
	{#if $auth.isAuthenticated}
		<!-- <nav>
			<div class="p-4 bg-blue-500 text-white">
				<a href="/">landing</a>
				<a href="/main">main</a>
				<a href="/member/myPage">myPage</a>
				<button onclick={() => auth.logout()}>logout</button>
			</div>
		</nav> -->
	{/if}

	{@render children()}
{/if}
