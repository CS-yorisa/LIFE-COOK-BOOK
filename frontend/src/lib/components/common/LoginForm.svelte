<script>
	import { Button } from '$lib/components/ui/button';
	import { auth } from '$lib/auth';
	import { goto } from '$app/navigation';

	if (localStorage.getItem('access')) {
		goto('/main');
	}

	let email = '';
	let password = '';
	let isLoading = false;
	let errorMessage = '';

	async function handleLogin() {
		if (!email || !password) {
			errorMessage = '이메일과 비밀번호를 입력해주세요.';
			return;
		}

		isLoading = true;
		errorMessage = '';

		try {
			await new Promise(resolve => setTimeout(resolve, 1000));

			const user = {
				email,
				password
			};

			auth.login(user);
			goto('/main');
		} catch (error) {
			errorMessage = '로그인에 실패했습니다. 다시 시도해주세요.';
		} finally {
			isLoading = false;
		}
	}

	function handleGitHubLogin() {
		console.log('GitHub login attempt');
		errorMessage = 'GitHub 로그인은 아직 구현되지 않았습니다.';
	}
</script>

<div class="w-full max-w-sm">
	<div class="mb-8 text-center">
		<h1 class="mb-2 text-2xl font-bold text-gray-900">Login to your account</h1>
		<p class="text-sm text-gray-600">Enter your email below to login to your account</p>
	</div>

	<form
		onsubmit={e => {
			e.preventDefault();
			handleLogin();
		}}
		class="space-y-6"
	>
		{#if errorMessage}
			<div class="rounded-lg border border-red-200 bg-red-50 px-4 py-3 text-sm text-red-700">
				{errorMessage}
			</div>
		{/if}

		<div>
			<label for="email" class="mb-2 block text-sm font-medium text-gray-900">Email</label>
			<input
				id="email"
				type="email"
				bind:value={email}
				placeholder="m@example.com"
				class="w-full rounded-lg border border-gray-300 px-3 py-2 text-sm focus:border-transparent focus:outline-none focus:ring-2 focus:ring-blue-500"
				required
			/>
		</div>

		<div>
			<label for="password" class="mb-2 block text-sm font-medium text-gray-900">Password</label>
			<div class="relative">
				<input
					id="password"
					type="password"
					bind:value={password}
					class="w-full rounded-lg border border-gray-300 px-3 py-2 text-sm focus:border-transparent focus:outline-none focus:ring-2 focus:ring-blue-500"
					required
				/>
				<div class="absolute right-3 top-1/2 -translate-y-1/2 transform">
					<a href="/member/findPassword" class="text-sm text-gray-600 hover:text-gray-800">
						Forgot your password?
					</a>
				</div>
			</div>
		</div>

		<button
			type="submit"
			disabled={isLoading}
			class="w-full rounded-lg bg-gray-900 px-4 py-2 text-sm font-medium text-white hover:bg-gray-800 disabled:cursor-not-allowed disabled:opacity-50"
		>
			{#if isLoading}
				<div
					class="mr-2 inline-block h-4 w-4 animate-spin rounded-full border-b-2 border-white"
				></div>
			{/if}
			{isLoading ? '로그인 중...' : 'Login'}
		</button>
	</form>

	<div class="mt-6">
		<div class="relative">
			<div class="absolute inset-0 flex items-center">
				<div class="w-full border-t border-gray-300"></div>
			</div>
			<div class="relative flex justify-center text-sm">
				<span class="bg-white px-2 text-gray-500">Or continue with</span>
			</div>
		</div>

		<div class="mt-6">
			<button
				onclick={handleGitHubLogin}
				class="flex w-full items-center justify-center gap-2 rounded-lg border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-900 hover:bg-gray-50"
			>
				<svg class="h-5 w-5" fill="currentColor" viewBox="0 0 20 20">
					<path
						fill-rule="evenodd"
						d="M10 0C4.477 0 0 4.484 0 10.017c0 4.425 2.865 8.18 6.839 9.504.5.092.682-.217.682-.483 0-.237-.008-.868-.013-1.703-2.782.605-3.369-1.343-3.369-1.343-.454-1.158-1.11-1.466-1.11-1.466-.908-.62.069-.608.069-.608 1.003.07 1.531 1.032 1.531 1.032.892 1.53 2.341 1.088 2.91.832.092-.647.35-1.088.636-1.338-2.22-.253-4.555-1.113-4.555-4.951 0-1.093.39-1.988 1.029-2.688-.103-.253-.446-1.272.098-2.65 0 0 .84-.27 2.75 1.026A9.564 9.564 0 0110 4.844c.85.004 1.705.115 2.504.337 1.909-1.296 2.747-1.027 2.747-1.027.546 1.379.203 2.398.1 2.651.64.7 1.028 1.595 1.028 2.688 0 3.848-2.339 4.695-4.566 4.942.359.31.678.921.678 1.856 0 1.338-.012 2.419-.012 2.747 0 .268.18.58.688.482A10.019 10.019 0 0020 10.017C20 4.484 15.522 0 10 0z"
						clip-rule="evenodd"
					></path>
				</svg>
				Login with GitHub
			</button>
		</div>
	</div>

	<div class="mt-6 text-center">
		<p class="text-sm text-gray-600">
			Don't have an account?
			<a href="/member/signUp" class="text-gray-600 underline hover:text-gray-800">Sign up</a>
		</p>
	</div>
</div>
