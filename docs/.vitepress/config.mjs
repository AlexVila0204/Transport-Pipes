import { defineConfig } from 'vitepress'

export default defineConfig({
  title: "Transport-Pipes Wiki",
  description: "Official Documentation for the Transport-Pipes Minecraft Plugin",
  
  // If hosting on GitHub Pages at <username>.github.io/Transport-Pipes, 
  // you must set the base to the repository name.
  base: '/Transport-Pipes/', 

  themeConfig: {
    nav: [
      { text: 'Home', link: '/' },
      { text: 'Tutorials', link: '/tutorials/installation' },
      { text: 'How-To', link: '/how-to/filtering-items' },
      { text: 'Reference', link: '/reference/pipe-types' },
      { text: 'Explanation', link: '/explanation/render-systems' }
    ],

    sidebar: [
      {
        text: 'Tutorial',
        collapsed: false,
        items: [
          { text: '1. Installation', link: '/tutorials/installation' },
          { text: '2. Getting Started', link: '/tutorials/getting-started' }
        ]
      },
      {
        text: 'How-To Guides',
        collapsed: false,
        items: [
          { text: 'Filter Items (Golden Pipes)', link: '/how-to/filtering-items' },
          { text: 'Automate Crafting', link: '/how-to/auto-crafting' },
          { text: 'Hide Pipes (Obfuscation)', link: '/how-to/hiding-pipes' },
          { text: 'Setup Resource Pack', link: '/how-to/setup-resourcepack' }
        ]
      },
      {
        text: 'Reference (Data & Specs)',
        collapsed: false,
        items: [
          { text: 'Pipe Types & Recipes', link: '/reference/pipe-types' },
          { text: 'Configuration', link: '/reference/configuration' },
          { text: 'Commands & Permissions', link: '/reference/commands' },
          { text: 'Developer API', link: '/reference/api' },
          { text: 'Visual Gallery', link: '/reference/visual-gallery' },
          { text: 'History & Dependencies', link: '/reference/history-dependencies' },
          { text: 'Credits & Contributors', link: '/reference/credits' }
        ]
      },
      {
        text: 'Explanation',
        collapsed: false,
        items: [
          { text: 'Render Systems (Vanilla vs Modeled)', link: '/explanation/render-systems' },
          { text: 'Performance & Tick System', link: '/explanation/performance' }
        ]
      }
    ],

    socialLinks: [
      { icon: 'github', link: 'https://github.com/AlexVila0204/Transport-Pipes' }
    ]
  }
})
