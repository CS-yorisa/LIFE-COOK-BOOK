<script>
	import { goto } from '$app/navigation';

	export let size = 'default';

	$: sizeClass =
		{
			small: 'text-4xl',
			default: 'text-6xl',
			large: 'text-8xl'
		}[size] || 'text-6xl';

	function handleLogoClick() {
		goto('/');
	}
</script>

<div
	class="logo-text {sizeClass} cursor-pointer transition-opacity hover:opacity-80"
	on:click={handleLogoClick}
>
	<div class="text-primary-foreground -mb-3">LIFE</div>
	<div class="text-muted-foreground -mb-3">COOK</div>
	<div class="text-accent-foreground">BOOK</div>
</div>
