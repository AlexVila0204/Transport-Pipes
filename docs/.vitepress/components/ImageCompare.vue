<template>
  <div
    class="image-compare"
    ref="container"
    @mousedown="startDrag"
    @touchstart.prevent="startDrag"
  >
    <!-- Bottom layer: Right image (Vanilla) -->
    <img :src="rightSrc" :alt="rightLabel" class="image-compare__right" draggable="false" />

    <!-- Top layer: Left image (Modeled), clipped by wrapper width -->
    <div class="image-compare__left-wrapper" :style="{ width: position + '%' }">
      <img
        :src="leftSrc"
        :alt="leftLabel"
        class="image-compare__left"
        :style="{ width: containerWidth + 'px' }"
        draggable="false"
      />
    </div>

    <!-- Vertical slider line only (no arrows) -->
    <div class="image-compare__slider" :style="{ left: position + '%' }">
      <div class="image-compare__line"></div>
      <div class="image-compare__knob"></div>
    </div>

    <!-- Labels -->
    <div class="image-compare__label image-compare__label--left">{{ leftLabel }}</div>
    <div class="image-compare__label image-compare__label--right">{{ rightLabel }}</div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'

defineProps({
  leftSrc: { type: String, required: true },
  rightSrc: { type: String, required: true },
  leftLabel: { type: String, default: 'Modeled' },
  rightLabel: { type: String, default: 'Vanilla' },
})

const container = ref(null)
const position = ref(50)
const containerWidth = ref(800)
let isDragging = false

function updateContainerWidth() {
  if (container.value) {
    containerWidth.value = container.value.getBoundingClientRect().width
  }
}

function getPosition(e) {
  const rect = container.value.getBoundingClientRect()
  const clientX = e.touches ? e.touches[0].clientX : e.clientX
  let pos = ((clientX - rect.left) / rect.width) * 100
  return Math.max(2, Math.min(98, pos))
}

function startDrag(e) {
  isDragging = true
  position.value = getPosition(e)
}

function onDrag(e) {
  if (!isDragging) return
  position.value = getPosition(e)
}

function stopDrag() {
  isDragging = false
}

onMounted(() => {
  updateContainerWidth()
  window.addEventListener('mousemove', onDrag)
  window.addEventListener('mouseup', stopDrag)
  window.addEventListener('touchmove', onDrag)
  window.addEventListener('touchend', stopDrag)
  window.addEventListener('resize', updateContainerWidth)
})

onUnmounted(() => {
  window.removeEventListener('mousemove', onDrag)
  window.removeEventListener('mouseup', stopDrag)
  window.removeEventListener('touchmove', onDrag)
  window.removeEventListener('touchend', stopDrag)
  window.removeEventListener('resize', updateContainerWidth)
})
</script>

<style scoped>
.image-compare {
  position: relative;
  width: 100%;
  overflow: hidden;
  cursor: col-resize;
  border-radius: 10px;
  border: 2px solid var(--vp-c-divider);
  user-select: none;
  -webkit-user-select: none;
  line-height: 0;
}

.image-compare__right {
  display: block;
  width: 100%;
  height: auto;
}

.image-compare__left-wrapper {
  position: absolute;
  top: 0;
  left: 0;
  height: 100%;
  overflow: hidden;
}

.image-compare__left {
  display: block;
  height: auto;
  max-width: none;
}

.image-compare__slider {
  position: absolute;
  top: 0;
  bottom: 0;
  width: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  z-index: 10;
}

.image-compare__line {
  position: absolute;
  top: 0;
  bottom: 0;
  width: 3px;
  background: white;
  box-shadow: 0 0 8px rgba(0, 0, 0, 0.6);
  transform: translateX(-50%);
}

.image-compare__knob {
  position: relative;
  width: 16px;
  height: 48px;
  border-radius: 8px;
  background: white;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.5);
  z-index: 11;
}

.image-compare__label {
  position: absolute;
  bottom: 12px;
  background: rgba(0, 0, 0, 0.7);
  color: white;
  padding: 5px 14px;
  border-radius: 5px;
  font-size: 0.85rem;
  font-weight: 600;
  pointer-events: none;
  z-index: 5;
}

.image-compare__label--left {
  left: 12px;
}

.image-compare__label--right {
  right: 12px;
}
</style>
