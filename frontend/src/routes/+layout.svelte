<script>
	let { children } = $props();
	import "../app.css";
</script>

<nav>
	<div class="p-4 bg-blue-500 text-white">
		<a href="/">landing</a>
		<a href="/main">main</a>
		<a href="/member/login">login</a>
	</div>
</nav>

{@render children()}
