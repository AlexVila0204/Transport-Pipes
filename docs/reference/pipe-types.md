# Pipe Types & Recipes

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'

const coloredPipes = ['white_pipe.png', 'red_pipe.png', 'yellow_pipe.png', 'green_pipe.png', 'black_pipe.png']
const currentIndex = ref(0)
const currentImage = ref(coloredPipes[0])

onMounted(() => {
  const interval = setInterval(() => {
    currentIndex.value = (currentIndex.value + 1) % coloredPipes.length
    currentImage.value = coloredPipes[currentIndex.value]
  }, 1500)
})
</script>

The Transport-Pipes plugin adds several different pipes. At the moment there are colored pipes, golden pipes, iron pipes, iron pipes, extraction pipes, void pipes, ice pipes, and crafting pipes.

Crafting recipes can be disabled in the config file which allows you to use any external "custom recipes" plugin to implement different recipes. Below are the default recipes and behaviors.

---

### Colored Pipe
**Behavior:** The simplest pipe. Its only purpose is to transport items. You can dye this kind of pipe. Different colored pipes behave the same but **do not connect to each other**, allowing compact routing. The *White* colored pipe is the default and connects to any other neighboring pipe.

<img :src="`/Transport-Pipes/images/${currentImage}`" alt="Colored Pipes" width="400" style="border-radius: 8px; margin-top: 10px; border: 1px solid #444;" />

---

### Extraction Pipe
**Behavior:** This is the only pipe which can *extract* items from container blocks (Chests, furnaces, hoppers, etc). It only has one extract direction. You can change conditions (Redstone required, always, never) using a Wrench. It connects with every other pipe, but **not** with other extraction pipes.

![Extraction Pipe](/images/extraction_pipe.png)

---

### Ice Pipe
**Behavior:** Transports items in the exact same way as colored pipes do, but **four times faster**.

![Ice Pipe](/images/ice_pipe.png)

---

### Iron Pipe
**Behavior:** Acts as a "One-Way-Pipe". Regardless of the direction an item comes from, it will always be forced to move into the single output direction. You can change this output direction by right-clicking the pipe with a wrench (marked by a yellow texture).

![Iron Pipe](/images/iron_pipe.png)

---

### Void Pipe
**Behavior:** Simply destroys and deletes all items which move inside of it. Useful for trash cans or overflow handling.

![Void Pipe](/images/void_pipe.png)

---

### Crafting Pipe
**Behavior:** Gives you the possibility to automate crafting processes. After specifying a recipe with a wrench, all items going into this pipe will be cached. The result item is automatically crafted and ejected as soon as enough ingredients are stored inside.

![Crafting Pipe](/images/craft_pipe.png)

---

### Golden Pipe
**Behavior:** The sorter pipe. Every output direction on the Golden Pipe has a specific color. By right-clicking with a wrench, a sorting GUI opens allowing you to filter items based on material and metadata.

![Golden Pipe](/images/gold_pipe.png)

---

### The Wrench
**Behavior:** Required to configure extraction conditions, pipe directions, crafting recipes, and golden pipe filters. Used simply by right-clicking the desired pipe.

![Wrench](/images/craft_wrench.png)
