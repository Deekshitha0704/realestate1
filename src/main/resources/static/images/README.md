# Real Estate Platform Images

This directory contains all image assets for the Real Estate Platform.

## Image Structure

```
images/
├── properties/           # Property photos and thumbnails
├── icons/               # UI icons and badges
├── backgrounds/         # Background images
└── team/                # Team member photos
```

## How to Add Images

1. **Property Images**: Place property photos in `properties/` folder
2. **UI Icons**: Add custom icons to `icons/` folder
3. **Backgrounds**: Add background images to `backgrounds/` folder

## Supported Formats

- `.jpg` - High quality photos
- `.png` - Images with transparency
- `.webp` - Modern compressed format
- `.svg` - Vector graphics

## Image Optimization Tips

1. Compress images before uploading
2. Use appropriate dimensions (properties: 800x600px)
3. Use WebP format for better performance
4. Provide alt text for accessibility

## Example Usage in HTML

```html
<!-- Property Image -->
<img src="/images/properties/property-001.jpg" alt="Modern Downtown Apartment">

<!-- Icon -->
<img src="/images/icons/bed.svg" alt="Bedrooms">

<!-- Background -->
<div style="background-image: url('/images/backgrounds/hero.jpg');"></div>
```

## Current Placeholder Usage

The application currently uses:
- Font Awesome icons for visual elements
- CSS gradients for property card backgrounds
- Emoji/Unicode for amenities display

You can replace these with actual images by:

1. Uploading images to respective subfolders
2. Updating references in `index.html` and `app.js`
3. Modifying CSS in `style.css` to use image URLs instead of gradients
