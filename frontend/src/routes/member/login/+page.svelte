<script>
	import Logo from '$lib/components/common/Logo.svelte';
	import LoginForm from '$lib/components/common/LoginForm.svelte';
</script>

<div class="flex min-h-screen flex-col">
	<!-- Top Section - Muted Background -->
	<div class="bg-muted-background relative h-[140px]">
		<!-- Fixed Logo at top 80px -->
		<div class="absolute left-0 right-0 top-[80px] flex justify-center">
			<div
				class="mx-auto flex w-full max-w-[1230px] items-center justify-between px-4 sm:px-6 lg:px-8"
			>
				<Logo size="large" />
				<div class="hidden lg:block lg:w-[700px]"></div>
				<!-- Spacer for Login Form - 큰 화면에서만 표시 -->
			</div>
		</div>
	</div>

	<!-- Bottom Section - Content Area -->
	<div class="flex-1 bg-white">
		<div
			class="mx-auto flex min-h-full w-full max-w-[1230px] items-center justify-center px-4 sm:px-6 lg:px-8"
		>
			<div class="flex w-full max-w-[1000px] items-center justify-between">
				<div class="hidden lg:block lg:w-[500px]"></div>
				<!-- Spacer for Logo alignment - 큰 화면에서만 표시 -->
				<div class="flex w-full items-end justify-center py-8 lg:w-[500px] lg:py-16">
					<LoginForm />
				</div>
			</div>
		</div>
	</div>
</div>
