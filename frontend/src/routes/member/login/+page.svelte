<script>
	import Logo from '$lib/components/common/Logo.svelte';
	import LoginForm from '$lib/components/common/LoginForm.svelte';
</script>

<div class="flex min-h-screen flex-col">
	<!-- Top Section - Muted Background -->
	<div class="bg-muted-background relative h-[140px]">
		<!-- Fixed Logo at top 80px -->
		<div class="absolute top-[80px] right-0 left-0 flex justify-center">
			<div class="flex min-w-[1500px] items-center justify-between px-8">
				<Logo size="large" />
				<div class="w-[700px]"></div>
				<!-- Spacer for Login Form -->
			</div>
		</div>
	</div>

	<!-- Bottom Section - Content Area -->
	<div class="flex-1 bg-white">
		<div class="flex min-h-full min-w-[1500px] items-center justify-center px-8">
			<div class="flex w-full max-w-[1000px] items-center justify-between">
				<div class="w-[500px]"></div>
				<!-- Spacer for Logo alignment -->
				<div class="flex w-[500px] items-end justify-center py-16">
					<LoginForm />
				</div>
			</div>
		</div>
	</div>
</div>
