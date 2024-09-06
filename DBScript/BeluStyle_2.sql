USE [master]
GO
/****** Object:  Database [BeluStyle]    Script Date: 7/14/2024 5:52:09 PM ******/
CREATE DATABASE [BeluStyle]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'BeluStyle', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\BeluStyle.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'BeluStyle_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\BeluStyle_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [BeluStyle] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [BeluStyle].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [BeluStyle] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [BeluStyle] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [BeluStyle] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [BeluStyle] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [BeluStyle] SET ARITHABORT OFF 
GO
ALTER DATABASE [BeluStyle] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [BeluStyle] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [BeluStyle] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [BeluStyle] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [BeluStyle] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [BeluStyle] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [BeluStyle] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [BeluStyle] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [BeluStyle] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [BeluStyle] SET  ENABLE_BROKER 
GO
ALTER DATABASE [BeluStyle] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [BeluStyle] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [BeluStyle] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [BeluStyle] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [BeluStyle] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [BeluStyle] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [BeluStyle] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [BeluStyle] SET RECOVERY FULL 
GO
ALTER DATABASE [BeluStyle] SET  MULTI_USER 
GO
ALTER DATABASE [BeluStyle] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [BeluStyle] SET DB_CHAINING OFF 
GO
ALTER DATABASE [BeluStyle] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [BeluStyle] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [BeluStyle] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [BeluStyle] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'BeluStyle', N'ON'
GO
ALTER DATABASE [BeluStyle] SET QUERY_STORE = ON
GO
ALTER DATABASE [BeluStyle] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [BeluStyle]
GO
/****** Object:  Table [dbo].[Brand]    Script Date: 7/14/2024 5:52:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Brand](
	[BrandID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[BrandID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Category]    Script Date: 7/14/2024 5:52:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Category](
	[CategoryID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[CategoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Order]    Script Date: 7/14/2024 5:52:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Order](
	[OrderID] [int] NOT NULL,
	[Username] [nvarchar](255) NOT NULL,
	[OrderDate] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[OrderID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OrderDetail]    Script Date: 7/14/2024 5:52:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OrderDetail](
	[OrderID] [int] NOT NULL,
	[ProductID] [int] NOT NULL,
	[Quantity] [int] NOT NULL,
	[Price] [decimal](18, 2) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[OrderID] ASC,
	[ProductID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Product]    Script Date: 7/14/2024 5:52:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Product](
	[ProductID] [int] IDENTITY(1,1) NOT NULL,
	[CategoryID] [int] NOT NULL,
	[BrandID] [int] NOT NULL,
	[Name] [nvarchar](255) NOT NULL,
	[Quantity] [int] NOT NULL,
	[Image] [nvarchar](255) NULL,
	[Price] [decimal](18, 2) NOT NULL,
	[Description] [nvarchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[ProductID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[User]    Script Date: 7/14/2024 5:52:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User](
	[Username] [nvarchar](255) NOT NULL,
	[Password] [nvarchar](255) NOT NULL,
	[FullName] [nvarchar](255) NOT NULL,
	[DOB] [date] NULL,
	[Sex] [varchar](6) NULL,
	[Email] [nvarchar](255) NOT NULL,
	[PhoneNumber] [nvarchar](20) NULL,
	[Address] [nvarchar](255) NULL,
	[Role] [nvarchar](50) NOT NULL,
	[Wallet] [decimal](18, 2) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Brand] ON 
GO
INSERT [dbo].[Brand] ([BrandID], [Name]) VALUES (1, N'Nike')
GO
INSERT [dbo].[Brand] ([BrandID], [Name]) VALUES (2, N'Adidas')
GO
INSERT [dbo].[Brand] ([BrandID], [Name]) VALUES (3, N'Zara')
GO
INSERT [dbo].[Brand] ([BrandID], [Name]) VALUES (4, N'Louis Vuitton')
GO
INSERT [dbo].[Brand] ([BrandID], [Name]) VALUES (5, N'Gucci')
GO
INSERT [dbo].[Brand] ([BrandID], [Name]) VALUES (6, N'Chanel')
GO
INSERT [dbo].[Brand] ([BrandID], [Name]) VALUES (7, N'Calvin Klein')
GO
INSERT [dbo].[Brand] ([BrandID], [Name]) VALUES (8, N'Supreme')
GO
INSERT [dbo].[Brand] ([BrandID], [Name]) VALUES (9, N'Dirty Coins')
GO
INSERT [dbo].[Brand] ([BrandID], [Name]) VALUES (10, N'Uniqlo')
GO
SET IDENTITY_INSERT [dbo].[Brand] OFF
GO
SET IDENTITY_INSERT [dbo].[Category] ON 
GO
INSERT [dbo].[Category] ([CategoryID], [Name]) VALUES (1, N'Shirt')
GO
INSERT [dbo].[Category] ([CategoryID], [Name]) VALUES (2, N'Pants')
GO
INSERT [dbo].[Category] ([CategoryID], [Name]) VALUES (3, N'Dress/Skirt')
GO
INSERT [dbo].[Category] ([CategoryID], [Name]) VALUES (4, N'Jacket')
GO
INSERT [dbo].[Category] ([CategoryID], [Name]) VALUES (5, N'Shoe')
GO
INSERT [dbo].[Category] ([CategoryID], [Name]) VALUES (6, N'Bag')
GO
INSERT [dbo].[Category] ([CategoryID], [Name]) VALUES (7, N'Hat')
GO
INSERT [dbo].[Category] ([CategoryID], [Name]) VALUES (8, N'Accessory')
GO
INSERT [dbo].[Category] ([CategoryID], [Name]) VALUES (9, N'Backpack')
GO
SET IDENTITY_INSERT [dbo].[Category] OFF
GO
INSERT [dbo].[Order] ([OrderID], [Username], [OrderDate]) VALUES (1, N'tomandmeow', CAST(N'2024-07-14T00:00:00.000' AS DateTime))
GO
INSERT [dbo].[Order] ([OrderID], [Username], [OrderDate]) VALUES (2, N'tomandmeow', CAST(N'2024-07-14T00:00:00.000' AS DateTime))
GO
INSERT [dbo].[Order] ([OrderID], [Username], [OrderDate]) VALUES (3, N'princess123', CAST(N'2024-07-14T00:00:00.000' AS DateTime))
GO
INSERT [dbo].[OrderDetail] ([OrderID], [ProductID], [Quantity], [Price]) VALUES (1, 1, 1, CAST(149000.00 AS Decimal(18, 2)))
GO
INSERT [dbo].[OrderDetail] ([OrderID], [ProductID], [Quantity], [Price]) VALUES (1, 4, 3, CAST(3897000.00 AS Decimal(18, 2)))
GO
INSERT [dbo].[OrderDetail] ([OrderID], [ProductID], [Quantity], [Price]) VALUES (1, 5, 3, CAST(2397000.00 AS Decimal(18, 2)))
GO
INSERT [dbo].[OrderDetail] ([OrderID], [ProductID], [Quantity], [Price]) VALUES (1, 6, 1, CAST(399000.00 AS Decimal(18, 2)))
GO
INSERT [dbo].[OrderDetail] ([OrderID], [ProductID], [Quantity], [Price]) VALUES (1, 15, 1, CAST(350000.00 AS Decimal(18, 2)))
GO
INSERT [dbo].[OrderDetail] ([OrderID], [ProductID], [Quantity], [Price]) VALUES (2, 3, 1, CAST(599000.00 AS Decimal(18, 2)))
GO
INSERT [dbo].[OrderDetail] ([OrderID], [ProductID], [Quantity], [Price]) VALUES (3, 2, 1, CAST(299000.00 AS Decimal(18, 2)))
GO
SET IDENTITY_INSERT [dbo].[Product] ON 
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (1, 1, 10, N'Basic Crew Neck T-Shirt', 96, N'1.jpg', CAST(149000.00 AS Decimal(18, 2)), N'Simple style')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (2, 1, 10, N'Short Sleeve Polo T-Shirt', 49, N'2.jpg', CAST(299000.00 AS Decimal(18, 2)), N'Casual wear')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (3, 2, 10, N'Blocktech Windproof Jacket', 19, N'3.jpg', CAST(599000.00 AS Decimal(18, 2)), N'Windproof')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (4, 2, 10, N'Light Down Jacket', 7, N'4.jpg', CAST(1299000.00 AS Decimal(18, 2)), N'Lightweight')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (5, 3, 10, N'EZY Straight Fit Jeans', 37, N'5.jpg', CAST(799000.00 AS Decimal(18, 2)), N'Comfort fit')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (6, 3, 10, N'Smart Ankle Jogger Pants', 58, N'6.jpg', CAST(399000.00 AS Decimal(18, 2)), N'Stylish joggers')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (7, 4, 10, N'Rayon Challis Dress', 30, N'7.jpg', CAST(599000.00 AS Decimal(18, 2)), N'Elegant dress')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (8, 4, 10, N'Cotton Lawn Pleated Skirt', 25, N'8.jpg', CAST(499000.00 AS Decimal(18, 2)), N'Pleated design')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (9, 5, 10, N'Heattech Extra Scarf', 15, N'9.jpg', CAST(399000.00 AS Decimal(18, 2)), N'Extra warm')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (10, 5, 10, N'Ribbed Beanie', 20, N'10.jpg', CAST(199000.00 AS Decimal(18, 2)), N'Cozy beanie')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (11, 1, 9, N'[DirtyCoins x B Ray] Girlfriend/Boyfriend T-Shirt', 99, N'11.jpg', CAST(350000.00 AS Decimal(18, 2)), N'Limited edition')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (12, 1, 9, N'DirtyCoins Gradient Regular T-Shirt - White', 4000, N'12.jpg', CAST(319000.00 AS Decimal(18, 2)), N'Gradient design')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (13, 1, 9, N'DirtyCoins Letters Monogram Denim Jersey Shirt - Black', 400, N'13.jpg', CAST(319000.00 AS Decimal(18, 2)), N'Monogram style')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (14, 1, 9, N'DirtyCoins Casual T-Shirt', 100, N'14.jpg', CAST(239000.00 AS Decimal(18, 2)), N'Everyday wear')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (15, 1, 9, N'DirtyCoins BaBy Dico T-Shirt', 499, N'15.jpg', CAST(350000.00 AS Decimal(18, 2)), N'Fun graphic')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (16, 2, 9, N'DirtyCoins Laurel Wreath Logo Pants - Cream', 500, N'16.jpg', CAST(699000.00 AS Decimal(18, 2)), N'Logo detail')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (17, 2, 9, N'DirtyCoins Baggy Jeans - Green Wash', 501, N'17.jpg', CAST(699000.00 AS Decimal(18, 2)), N'Baggy fit')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (18, 2, 9, N'DirtyCoins Slimfit Jeans', 501, N'18.jpg', CAST(569000.00 AS Decimal(18, 2)), N'Slim fit')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (19, 2, 9, N'DirtyCoins Dico Starry Jogger Pants', 200, N'19.jpg', CAST(349300.00 AS Decimal(18, 2)), N'Starry pattern')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (20, 2, 9, N'DirtyCoins Letters Monogram Denim Shorts - Black', 100, N'20.jpg', CAST(429000.00 AS Decimal(18, 2)), N'Monogram print')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (21, 4, 9, N'DirtyCoins Checkerboard Knit Cardigan', 150, N'21.jpg', CAST(719000.00 AS Decimal(18, 2)), N'Checkerboard')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (22, 4, 9, N'DirtyCoins Dico Puppies Hangouts Raglan Hoodie - White/Black', 200, N'22.jpg', CAST(599000.00 AS Decimal(18, 2)), N'Raglan hoodie')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (23, 4, 9, N'DirtyCoins Logo Relaxed Hoodie', 199, N'23.jpg', CAST(549000.00 AS Decimal(18, 2)), N'Relaxed fit')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (24, 4, 9, N'DirtyCoins Bandana Shirt Jacket - Black', 99, N'24.jpg', CAST(649000.00 AS Decimal(18, 2)), N'Bandana print')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (25, 4, 9, N'DirtyCoins Club Raglan Hoodie - Black/White', 40, N'25.jpg', CAST(599000.00 AS Decimal(18, 2)), N'Club design')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (26, 3, 9, N'DirtyCoins Logo Plaid Tennis Skirt', 70, N'26.jpg', CAST(299000.00 AS Decimal(18, 2)), N'Plaid skirt')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (27, 6, 9, N'DirtyCoins Logo Crossbody Bag', 700, N'27.jpg', CAST(467500.00 AS Decimal(18, 2)), N'Crossbody style')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (28, 6, 9, N'DirtyCoins Logo Bowler Bag', 100, N'28.jpg', CAST(1190000.00 AS Decimal(18, 2)), N'Bowler shape')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (29, 9, 9, N'DirtyCoins Y Pattern Signature Backpack - B Ray', 100, N'29.jpg', CAST(798000.00 AS Decimal(18, 2)), N'Signature style')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (30, 9, 9, N'DirtyCoins Y Pattern Signature Backpack - Dirty Coins', 100, N'30.jpg', CAST(749000.00 AS Decimal(18, 2)), N'Y Pattern')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (31, 9, 9, N'DirtyCoins Dico Wavy Backpack V3', 100, N'31.jpg', CAST(650000.00 AS Decimal(18, 2)), N'Wavy design')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (32, 7, 9, N'DirtyCoins Y Logo Cap - Black', 200, N'32.jpg', CAST(219000.00 AS Decimal(18, 2)), N'Logo cap')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (33, 8, 9, N'DirtyCoins Sticker Pack', 1000, N'33.jpg', CAST(39000.00 AS Decimal(18, 2)), N'Fun stickers')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (34, 5, 9, N'DirtyCoins Print Slides (Color: Black, Coffee) (Size: 36, 38, 39, 40, 41, 43, 44, 45)', 1000, N'34.jpg', CAST(350000.00 AS Decimal(18, 2)), N'Printed slides')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (35, 5, 9, N'DirtyCoins Print Slides (Color: Black, Coffee) (Size: 42)', 1000, N'35.jpg', CAST(420000.00 AS Decimal(18, 2)), N'Printed slides')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (36, 8, 9, N'2024 Red Envelopes - Pack of 5', 1000, N'36.jpg', CAST(39000.00 AS Decimal(18, 2)), N'Festive pack')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (37, 6, 9, N'DirtyCoins Signature Woman Shoulder Bag - Black', 100, N'37.jpg', CAST(699000.00 AS Decimal(18, 2)), N'Signature bag')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (38, 7, 9, N'DirtyCoins Leather Patch Beanie', 50, N'38.jpg', CAST(300000.00 AS Decimal(18, 2)), N'Leather patch')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (39, 1, 7, N'Men''s Ar- Micro Monologo Tee J322761BEH', 47, N'39.jpg', CAST(1422685.00 AS Decimal(18, 2)), N'Micro monologo')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (40, 1, 7, N'Men''s Compact Cotton T-Shirt 40611STDAJ', 35, N'40.jpg', CAST(931935.00 AS Decimal(18, 2)), N'Compact cotton')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (41, 1, 7, N'Men''s A- Ss Unisex Heart Tee J400222YAF', 23, N'41.jpg', CAST(1422685.00 AS Decimal(18, 2)), N'Unisex heart')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (42, 1, 7, N'Men''s A-Ss Unisex Rabbit Logo Tee J400232XA8', 56, N'42.jpg', CAST(1422685.00 AS Decimal(18, 2)), N'Rabbit logo')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (43, 1, 7, N'Men''s A- Ss Rel Triple Ck Logo Tee J323207YBH', 78, N'43.jpg', CAST(1785643.00 AS Decimal(18, 2)), N'Triple CK')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (44, 1, 8, N'Supreme Balloon T-Shirt - Heather Gray', 56, N'44.jpg', CAST(2000000.00 AS Decimal(18, 2)), N'Balloon graphic')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (45, 1, 8, N'Supreme Colorblock Soccer Polo - Purple', 38, N'45.jpg', CAST(2000000.00 AS Decimal(18, 2)), N'Colorblock design')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (46, 1, 8, N'Women''s Ar- Micro Monologo Slim Fit Tee J221217BEH', 5, N'46.jpg', CAST(735635.00 AS Decimal(18, 2)), N'Slim fit')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (47, 4, 7, N'Unisex Lsr R Dnm Jacket J4002351BJ', 10, N'47.jpg', CAST(7163969.00 AS Decimal(18, 2)), N'Denim jacket')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (48, 4, 7, N'Ar- B Clrbck Zip Hoodie J323390BEH', 29, N'48.jpg', CAST(2256960.00 AS Decimal(18, 2)), N'Colorblock zip')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (49, 4, 8, N'Supreme Hockey Hoodie - Black', 55, N'49.jpg', CAST(5500000.00 AS Decimal(18, 2)), N'Hockey style')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (50, 2, 8, N'Supreme Patchwork Sweatpants (FW20) - Black', 30, N'50.jpg', CAST(5000000.00 AS Decimal(18, 2)), N'Patchwork')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (51, 6, 8, N'Supreme SS24 Shoulder Bag - Green', 40, N'51.jpg', CAST(2500000.00 AS Decimal(18, 2)), N'SS24 edition')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (52, 6, 8, N'Supreme SS21 Shoulder Bag - Blue', 42, N'52.jpg', CAST(2000000.00 AS Decimal(18, 2)), N'SS21 edition')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (53, 9, 8, N'Supreme SS21 Backpack - Red Camo', 10, N'53.jpg', CAST(4500000.00 AS Decimal(18, 2)), N'Red camo')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (54, 9, 8, N'Supreme FW21 Backpack - Black', 20, N'54.jpg', CAST(3000000.00 AS Decimal(18, 2)), N'FW21 edition')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (55, 3, 1, N'Women''s Fashion Mini Skirt AS W NSW AIR WVN HR', 15, N'55.jpg', CAST(743000.00 AS Decimal(18, 2)), N'Fashion mini')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (56, 3, 1, N'Women''s Nike Court Dri-Fit Victory Flouncy Tennis Skirt - White', 30, N'56.jpg', CAST(1349000.00 AS Decimal(18, 2)), N'Tennis skirt')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (57, 3, 1, N'Women''s Nike High-Waisted Woven Skirt - Yellow', 50, N'57.jpg', CAST(743000.00 AS Decimal(18, 2)), N'High-waisted')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (58, 4, 1, N'NOCTA Hoodie', 33, N'58.jpg', CAST(2759000.00 AS Decimal(18, 2)), N'NOCTA design')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (59, 2, 1, N'NOCTA Men''s Fleece Pants', 34, N'59.jpg', CAST(2499000.00 AS Decimal(18, 2)), N'Fleece pants')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (60, 5, 1, N'Men''s Flip-Flops', 55, N'60.jpg', CAST(1479000.00 AS Decimal(18, 2)), N'Summer wear')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (61, 5, 1, N'Basketball Shoes', 13, N'61.jpg', CAST(5869000.00 AS Decimal(18, 2)), N'Pro performance')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (62, 5, 1, N'Women''s Nike In-Season TR 13 PRM Training Shoes', 20, N'62.jpg', CAST(1644299.00 AS Decimal(18, 2)), N'Training shoes')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (63, 1, 1, N'Women''s Nike Dri-FIT Swoosh Short-Sleeve Running Top', 100, N'63.jpg', CAST(969000.00 AS Decimal(18, 2)), N'Running top')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (64, 1, 1, N'Men''s Dri-FIT Nike Primary Long-Sleeve Multi-Purpose T-Shirt', 57, N'64.jpg', CAST(1379000.00 AS Decimal(18, 2)), N'Multi-purpose')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (65, 1, 1, N'Women''s Nike One Patterned Running Tank Top', 115, N'65.jpg', CAST(919000.00 AS Decimal(18, 2)), N'Patterned tank')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (66, 1, 1, N'Men''s Nike Dri-FIT Victory Long-Sleeve Golf Shirt', 20, N'66.jpg', CAST(1529000.00 AS Decimal(18, 2)), N'Golf shirt')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (67, 5, 1, N'Women''s Nike GP Challenge 1 Hard Court Tennis Shoes', 15, N'67.jpg', CAST(4699000.00 AS Decimal(18, 2)), N'Tennis shoes')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (68, 5, 1, N'Women''s Nike Motiva Walking Shoes', 27, N'68.jpg', CAST(3239000.00 AS Decimal(18, 2)), N'Walking shoes')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (69, 5, 1, N'Men''s Nike Dunk Low Retro Shoes', 47, N'69.jpg', CAST(2929000.00 AS Decimal(18, 2)), N'Retro style')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (70, 5, 1, N'Men''s Nike Defy All Day Walking Shoes', 100, N'70.jpg', CAST(1909000.00 AS Decimal(18, 2)), N'Everyday wear')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (71, 5, 1, N'Men''s Nike React Phantom Run Flyknit Running Shoes', 12, N'71.jpg', CAST(4109000.00 AS Decimal(18, 2)), N'Flyknit design')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (72, 7, 2, N'Three-Stripes Metallic Baseball Cap', 198, N'72.jpg', CAST(500000.00 AS Decimal(18, 2)), N'Metallic look')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (73, 7, 2, N'Four-Panel Mesh Running Cap AEROREADY', 278, N'73.jpg', CAST(550000.00 AS Decimal(18, 2)), N'Mesh design')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (74, 7, 2, N'W PRINTED Golf Bucket Hat', 78, N'74.jpg', CAST(900000.00 AS Decimal(18, 2)), N'Golf style')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (75, 7, 2, N'Twill Cap', 37, N'75.jpg', CAST(850000.00 AS Decimal(18, 2)), N'Twill fabric')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (76, 9, 2, N'PE BP Backpack', 55, N'76.jpg', CAST(1500000.00 AS Decimal(18, 2)), N'PE edition')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (77, 9, 2, N'Xplorer Backpack', 43, N'77.png', CAST(1500000.00 AS Decimal(18, 2)), N'XPLORER BACKPACK')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (78, 9, 2, N'Backpack Adicolor', 71, N'78.png', CAST(850000.00 AS Decimal(18, 2)), N'Adidas backpack Adicolor')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (79, 2, 2, N'Parachute Straight Pants', 66, N'79.png', CAST(2300000.00 AS Decimal(18, 2)), N'Adidas parachute straight pants')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (80, 2, 2, N'Track Pants Nylon Adicolor', 23, N'80.png', CAST(1900000.00 AS Decimal(18, 2)), N'Adidas track pants nylon Adicolor')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (81, 5, 2, N'Astir SN Shoes', 132, N'81.png', CAST(1680000.00 AS Decimal(18, 2)), N'Adidas Astir SN shoes')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (82, 4, 2, N'Blouson Straight Jacket SST', 33, N'82.png', CAST(2400000.00 AS Decimal(18, 2)), N'Adidas blouson straight jacket SST')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (83, 1, 3, N'Satin Pleated Shirt', 123, N'83.png', CAST(630000.00 AS Decimal(18, 2)), N'Zara satin pleated shirt')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (84, 3, 3, N'Zara White Embroidered Maxi Dress', 75, N'84.png', CAST(620000.00 AS Decimal(18, 2)), N'Zara white embroidered maxi dress')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (85, 3, 3, N'Long Embroidered Floral Maxi Dress', 135, N'85.png', CAST(445000.00 AS Decimal(18, 2)), N'Zara long embroidered floral maxi dress')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (86, 4, 3, N'Zara Suede Leather Jacket (Belt Model)', 95, N'86.png', CAST(1199000.00 AS Decimal(18, 2)), N'Zara suede leather jacket (belt model)')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (87, 1, 3, N'Black Wide 4-Button Vest Limited Edition', 25, N'87.png', CAST(1450000.00 AS Decimal(18, 2)), N'Zara black wide 4-button vest limited edition')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (88, 3, 3, N'Long Shirt Dress with Gray Snake Pattern Zara Auth New Tag', 55, N'88.png', CAST(1050000.00 AS Decimal(18, 2)), N'Zara long shirt dress with gray snake pattern')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (89, 1, 3, N'Zara Backless Long Sleeve Shirt', 65, N'89.png', CAST(358000.00 AS Decimal(18, 2)), N'Zara backless long sleeve shirt')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (90, 3, 3, N'Zara Jean Skirt', 22, N'90.png', CAST(440000.00 AS Decimal(18, 2)), N'Zara jean skirt')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (91, 1, 3, N'Zara Croptop Pocket Shirt', 203, N'91.png', CAST(350000.00 AS Decimal(18, 2)), N'Zara croptop pocket shirt')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (92, 2, 3, N'Zara Authentic Flared Jeans', 43, N'92.png', CAST(770000.00 AS Decimal(18, 2)), N'Zara authentic flared jeans')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (93, 8, 4, N'Louis Vuitton Zippy Organizer Wallet M82081 Black', 12, N'93.png', CAST(25500000.00 AS Decimal(18, 2)), N'Louis Vuitton zippy organizer wallet M82081 black')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (94, 8, 4, N'Louis Vuitton Monogram Silver Scarf Black', 35, N'94.png', CAST(16000000.00 AS Decimal(18, 2)), N'Louis Vuitton monogram silver scarf black')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (95, 6, 4, N'Louis Vuitton Madeleine BB Shoulder Bag M45977 Black', 10, N'95.png', CAST(81000000.00 AS Decimal(18, 2)), N'Louis Vuitton Madeleine BB shoulder bag M45977 black')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (96, 8, 4, N'Louis Vuitton Tambour Watch Q118N Black', 1, N'96.png', CAST(94500000.00 AS Decimal(18, 2)), N'Louis Vuitton Tambour watch Q118N black')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (97, 4, 4, N'Louis Vuitton Monogram Hoodie 1AAKV0 Black Patterned', 25, N'97.png', CAST(63000000.00 AS Decimal(18, 2)), N'Louis Vuitton monogram hoodie 1AAKV0 black patterned')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (98, 1, 4, N'Louis Vuitton Shirt 1AFAXK White', 25, N'98.png', CAST(29450000.00 AS Decimal(18, 2)), N'Louis Vuitton shirt 1AFAXK white')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (99, 7, 4, N'Louis Vuitton Damier Pop Cap M7360M Red Black Size M', 5, N'99.png', CAST(22450000.00 AS Decimal(18, 2)), N'Louis Vuitton Damier Pop cap M7360M red black size M')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (100, 8, 4, N'Louis Vuitton 1.1 Millionaires Sunglasses Z1166W White Gold', 62, N'100.png', CAST(22750000.00 AS Decimal(18, 2)), N'Louis Vuitton 1.1 Millionaires sunglasses Z1166W white gold')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (101, 8, 4, N'Louis Vuitton Initials Taurillon 40mm Reversible Belt M0532U Navy', 325, N'101.png', CAST(18450000.00 AS Decimal(18, 2)), N'Louis Vuitton initials Taurillon 40mm reversible belt M0532U navy')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (102, 6, 4, N'Louis Vuitton Trunk Messenger Bag M45727 Black', 40, N'102.png', CAST(63000000.00 AS Decimal(18, 2)), N'Louis Vuitton trunk messenger bag M45727 black')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (103, 5, 4, N'Louis Vuitton Black Leather Upper Case Loafers', 25, N'103.png', CAST(9450000.00 AS Decimal(18, 2)), N'Louis Vuitton black leather upper case loafers')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (104, 5, 5, N'Gucci Web Slide Sandal Black Size 41.5', 80, N'104.png', CAST(8000000.00 AS Decimal(18, 2)), N'Gucci web slide sandal black size 41.5')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (105, 5, 5, N'Gucci Black Rubber Buckle Strap Sandal Black 463463-J8700-1000', 68, N'105.png', CAST(18300000.00 AS Decimal(18, 2)), N'Gucci black rubber buckle strap sandal black 463463-J8700-1000')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (106, 5, 5, N'Gucci GG Supreme Slide Tiger 407345-G0K00-1084', 60, N'106.png', CAST(14500000.00 AS Decimal(18, 2)), N'Gucci GG Supreme slide Tiger 407345-G0K00-1084')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (107, 5, 5, N'Gucci Women’s Slide Sandal with Double G 619893-2HK80-9795', 70, N'107.png', CAST(16900000.00 AS Decimal(18, 2)), N'Gucci women’s slide sandal with double G 619893-2HK80-9795')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (108, 1, 5, N'Gucci GG Cotton Silk Polo ?742384 XJFGU 2270 Brown Beige', 110, N'108.png', CAST(24500000.00 AS Decimal(18, 2)), N'Gucci GG cotton silk polo ?742384 XJFGU 2270 brown beige')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (109, 1, 5, N'Gucci Polo Shirt Black Size M', 120, N'109.png', CAST(17500000.00 AS Decimal(18, 2)), N'Gucci polo shirt black size M')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (110, 1, 5, N'Gucci Polo Shirt Ss2020 Beige Size S', 96, N'110.png', CAST(15490000.00 AS Decimal(18, 2)), N'Gucci polo shirt SS2020 beige size S')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (111, 1, 5, N'Gucci GG Jacquard Cotton Jacket 496919 X9V05 4245', 69, N'111.png', CAST(32500000.00 AS Decimal(18, 2)), N'Gucci GG Jacquard cotton jacket 496919 X9V05 4245')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (112, 2, 5, N'Gucci GG Cotton 60s Trousers With Web Khaki', 142, N'112.png', CAST(15800000.00 AS Decimal(18, 2)), N'Gucci GG cotton 60s trousers with web khaki')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (113, 2, 5, N'Gucci Ken Scott Printed Silk Shorts Black Size 46', 135, N'113.png', CAST(5200000.00 AS Decimal(18, 2)), N'Gucci Ken Scott printed silk shorts black size 46')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (114, 2, 5, N'Gucci GG Nylon Swim Short With Bee 410571 XR898 4824 Dark Blue', 160, N'114.png', CAST(8950000.00 AS Decimal(18, 2)), N'Gucci GG nylon swim short with bee 410571 XR898 4824 dark blue')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (115, 4, 5, N'Gucci GG Jacquard Jacket New Gucci GG', 110, N'115.png', CAST(69974000.00 AS Decimal(18, 2)), N'Gucci GG Jacquard jacket new Gucci GG')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (116, 4, 5, N'Gucci GG Panel Track Jacket 693022 XJD9V-2270 Multi-color', 97, N'116.png', CAST(43800000.00 AS Decimal(18, 2)), N'Gucci GG panel track jacket 693022 XJD9V-2270 multi-color')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (117, 4, 5, N'Gucci Technical Jersey Jacket', 100, N'117.png', CAST(21600000.00 AS Decimal(18, 2)), N'Gucci technical jersey jacket')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (118, 4, 5, N'Gucci GG Jacket 655159 Green Yellow Size S', 100, N'118.png', CAST(60500000.00 AS Decimal(18, 2)), N'Gucci GG jacket 655159 green yellow size S')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (119, 4, 5, N'Gucci Sweatshirt With Logo 653367 Green Size S', 102, N'119.png', CAST(33500000.00 AS Decimal(18, 2)), N'Gucci sweatshirt with logo 653367 green size S')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (120, 4, 5, N'Gucci GG Monogram Monogram Reversible Jacket', 55, N'120.png', CAST(46298000.00 AS Decimal(18, 2)), N'Gucci GG monogram monogram reversible jacket')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (121, 6, 5, N'Gucci Ophidia Small Top Handle Bag with Web Brown Beige', 127, N'121.png', CAST(58500000.00 AS Decimal(18, 2)), N'Gucci Ophidia small top handle bag with web brown beige')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (122, 6, 5, N'Gucci 1955 Horsebit Shoulder Bag', 99, N'122.png', CAST(80900000.00 AS Decimal(18, 2)), N'Gucci 1955 Horsebit shoulder bag')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (123, 6, 5, N'Gucci 1955 Horsebit Shoulder Bag Light Brown', 80, N'123.png', CAST(62500000.00 AS Decimal(18, 2)), N'Gucci 1955 Horsebit shoulder bag light brown')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (124, 6, 5, N'Gucci 2way Handbag Micro Shima White Satchel', 117, N'124.png', CAST(22900000.00 AS Decimal(18, 2)), N'Gucci 2way handbag Micro Shima white satchel')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (125, 7, 5, N'Gucci GG Baseball Hat With Tiger Print Black Yellow Size M', 120, N'125.png', CAST(13000000.00 AS Decimal(18, 2)), N'Gucci GG baseball hat with Tiger print black yellow size M')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (126, 7, 5, N'Gucci GG Supreme Baseball Hat With Angry Cat Size M', 100, N'126.png', CAST(35000000.00 AS Decimal(18, 2)), N'Gucci GG Supreme baseball hat with Angry Cat size M')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (127, 7, 5, N'Gucci Tigers GG Baseball Hat 426887 4HB13 2160 Multi-color Size S', 37, N'127.png', CAST(10159000.00 AS Decimal(18, 2)), N'Gucci Tigers GG baseball hat 426887 4HB13 2160 multi-color size S')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (128, 7, 5, N'Gucci Original GG Canvas Baseball With Web Beige Size L M S', 64, N'128.png', CAST(21590000.00 AS Decimal(18, 2)), N'Gucci original GG canvas baseball with web beige size L M S')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (129, 7, 5, N'Gucci Print Gucci Leather Baseball Size L M S', 19, N'129.png', CAST(20590000.00 AS Decimal(18, 2)), N'Gucci print Gucci leather baseball size L M S')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (130, 9, 5, N'Gucci GG Supreme Backpack 704017 FAA0R 9795 Brown', 55, N'130.png', CAST(42000000.00 AS Decimal(18, 2)), N'Gucci GG Supreme backpack 704017 FAA0R 9795 brown')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (131, 9, 5, N'Gucci Small Ophidia GG Supreme Backpack Brown', 81, N'131.png', CAST(40500000.00 AS Decimal(18, 2)), N'Gucci small Ophidia GG Supreme backpack brown')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (132, 6, 6, N'Chanel Classic Flap Bag Medium (Gold Lock)', 12, N'132.png', CAST(230000000.00 AS Decimal(18, 2)), N'Chanel classic flap bag medium (gold lock)')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (133, 6, 6, N'Chanel Hobo 22 Shoulder Bag Black (Silver Lock)', 30, N'133.png', CAST(150000000.00 AS Decimal(18, 2)), N'Chanel Hobo 22 shoulder bag black (silver lock)')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (134, 6, 6, N'Chanel Vanity Top Caviar Handle Black', 15, N'134.png', CAST(160000000.00 AS Decimal(18, 2)), N'Chanel Vanity top caviar handle black')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (135, 6, 6, N'Chanel Mini Womens Handbag Rectangular Green Lambskin Rainbow Hardware 2021', 20, N'135.png', CAST(168822000.00 AS Decimal(18, 2)), N'Chanel Mini Womens Handbag Rectangular Green Lambskin Rainbow Hardware 2021')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (136, 6, 6, N'Chanel Top Handle Bag 24P Black for Women', 41, N'136.png', CAST(166000000.00 AS Decimal(18, 2)), N'Chanel Top Handle Bag 24P Black for Women')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (137, 6, 6, N'Chanel Flap Bag With Top Handle Coco 10.5 Pearly', 33, N'137.png', CAST(137000000.00 AS Decimal(18, 2)), N'Chanel Flap Bag With Top Handle Coco 10.5 Pearly')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (138, 5, 6, N'Chanel Ankle Boots', 79, N'138.png', CAST(47000000.00 AS Decimal(18, 2)), N'Chanel Ankle Boots')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (139, 5, 6, N'Womens Loafers Chanel CC Gray Patent Loafers Gray Size 37', 80, N'139.png', CAST(38500000.00 AS Decimal(18, 2)), N'Women''s Loafers Chanel CC Gray Patent Loafers Gray Size 37')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (140, 8, 6, N'Chanel Metal Black CC Women''s Belt with Stone 3cm Color Black Size 80', 30, N'140.png', CAST(31500000.00 AS Decimal(18, 2)), N'Chanel Metal Black CC Women''s Belt with Stone 3cm Color Black Size 80')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (141, 8, 6, N'Chanel CC Women''s Choker Necklace Full White Pearl', 54, N'141.png', CAST(55000000.00 AS Decimal(18, 2)), N'Chanel CC Women''s Choker Necklace Full White Pearl')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (142, 8, 6, N'Chanel Necklace Pendant CC Yellow', 55, N'142.png', CAST(26500000.00 AS Decimal(18, 2)), N'Chanel Necklace Pendant CC Yellow')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (143, 8, 6, N'Chanel CC Necklace Black Yellow', 63, N'143.png', CAST(29000000.00 AS Decimal(18, 2)), N'Chanel CC Necklace Black Yellow')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (144, 8, 6, N'Chanel Pearl Necklace with Stone CC VCH6 White Ivory', 93, N'144.png', CAST(27500000.00 AS Decimal(18, 2)), N'Chanel Pearl Necklace with Stone CC VCH6 White Ivory')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (145, 8, 6, N'Chanel KT C Square Flower Earrings with Stones KTC120 White Yellow', 88, N'145.png', CAST(21500000.00 AS Decimal(18, 2)), N'Chanel KT C Square Flower Earrings with Stones KTC120 White Yellow')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (146, 8, 6, N'Chanel CC Logo Square Stone Earrings in White Gold', 101, N'146.png', CAST(22000000.00 AS Decimal(18, 2)), N'Chanel CC Logo Square Stone Earrings in White Gold')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (147, 8, 6, N'Chanel CC Mark Jewelry Yellow Earrings', 100, N'147.png', CAST(17800000.00 AS Decimal(18, 2)), N'Chanel CC Mark Jewelry Yellow Earrings')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (148, 7, 6, N'Chanel Classic Cashmere Wool Double C Logo Print Hat Grey', 110, N'148.png', CAST(18500000.00 AS Decimal(18, 2)), N'Chanel Classic Cashmere Wool Double C Logo Print Hat Grey')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (149, 9, 6, N'Chanel Mini Duma Backpack Black', 72, N'149.png', CAST(150000000.00 AS Decimal(18, 2)), N'Chanel Mini Duma Backpack Black')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (150, 9, 6, N'Chanel Backpack Grained Calfskin & Gold Black', 90, N'150.png', CAST(158000000.00 AS Decimal(18, 2)), N'Chanel Backpack Grained Calfskin & Gold Black')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (151, 9, 6, N'Chanel Small Backpack Lambskin & Gold Gray', 111, N'151.png', CAST(158000000.00 AS Decimal(18, 2)), N'Chanel Small Backpack Lambskin & Gold Gray')
GO
INSERT [dbo].[Product] ([ProductID], [CategoryID], [BrandID], [Name], [Quantity], [Image], [Price], [Description]) VALUES (152, 9, 6, N'Chanel Small Backpack Lambskin & Gold White', 104, N'152.png', CAST(158000000.00 AS Decimal(18, 2)), N'Chanel Small Backpack Lambskin & Gold White')
GO
SET IDENTITY_INSERT [dbo].[Product] OFF
GO
INSERT [dbo].[User] ([Username], [Password], [FullName], [DOB], [Sex], [Email], [PhoneNumber], [Address], [Role], [Wallet]) VALUES (N'arii3123', N'691344282201b4cceee6e8ba59add03822e35a1eb7cea0a5e5865549aee880e6', N'Võ Trương Nhật Đăng', CAST(N'2004-03-31' AS Date), N'Male', N'dangvtnce181699@fpt.edu.vn', N'0946680941', N'Cà Mau', N'Admin', CAST(765865488.00 AS Decimal(18, 2)))
GO
INSERT [dbo].[User] ([Username], [Password], [FullName], [DOB], [Sex], [Email], [PhoneNumber], [Address], [Role], [Wallet]) VALUES (N'buithinhu123', N'63839aefa88eb208ee8acb7bd63f9acf85927518c2230409f5338ba260f5f0c4', N'Bùi Thị Như', CAST(N'1995-03-25' AS Date), N'Female', N'buithinhu123@gmail.com', N'0791115717', N'Hải Phòng', N'User', CAST(548755681.00 AS Decimal(18, 2)))
GO
INSERT [dbo].[User] ([Username], [Password], [FullName], [DOB], [Sex], [Email], [PhoneNumber], [Address], [Role], [Wallet]) VALUES (N'chauvantung012', N'06384077606f3f71d53ab6da9f2d4414e23786150e71361a2080b363c5522dd0', N'Châu Văn Tùng', CAST(N'1979-06-10' AS Date), N'Male', N'chauvantung012@gmail.com', N'0989207533', N'Bà Rịa - Vũng Tàu', N'User', CAST(6912130400.00 AS Decimal(18, 2)))
GO
INSERT [dbo].[User] ([Username], [Password], [FullName], [DOB], [Sex], [Email], [PhoneNumber], [Address], [Role], [Wallet]) VALUES (N'dangthang456', N'6587a01f0d02251453f14af3f4856630f38447622e8b90d7e6b1f817afa31ed4', N'Đặng Văn Thắng', CAST(N'1985-05-15' AS Date), N'Male', N'dangvanthang987@gmail.com', N'0846569967', N'Đồng Nai', N'User', CAST(419440875.00 AS Decimal(18, 2)))
GO
INSERT [dbo].[User] ([Username], [Password], [FullName], [DOB], [Sex], [Email], [PhoneNumber], [Address], [Role], [Wallet]) VALUES (N'dothilan321', N'895c05cd6240066a6419b73ba0926ee9ff8c1b9072304dac7a16c63ec4520b0d', N'Đỗ Thị Lan', CAST(N'1990-03-17' AS Date), N'Female', N'dothilan321@gmail.com', N'0312363701', N'Hà Nam', N'User', CAST(46039500.00 AS Decimal(18, 2)))
GO
INSERT [dbo].[User] ([Username], [Password], [FullName], [DOB], [Sex], [Email], [PhoneNumber], [Address], [Role], [Wallet]) VALUES (N'hoangthiloan456', N'5bfca6a67bf65f6ef88062cf83ca8910d964292300869c196fa3693f61654843', N'Hoàng Thị Loan', CAST(N'1972-02-11' AS Date), N'Female', N'hoangthiloan456@gmail.com', N'0374666559', N'Thái Bình', N'User', CAST(2019243000.00 AS Decimal(18, 2)))
GO
INSERT [dbo].[User] ([Username], [Password], [FullName], [DOB], [Sex], [Email], [PhoneNumber], [Address], [Role], [Wallet]) VALUES (N'katorivn321', N'4697327cdd5bc21216e28e8fdb4d71d8b0e9d181786b18e478d112b5181b4174', N'Dương Nhật Anh', CAST(N'2004-12-22' AS Date), N'Male', N'anhdnce181079@fpt.edu.vn', N'0379697687', N'Sóc Trăng', N'Admin', CAST(9999999999.00 AS Decimal(18, 2)))
GO
INSERT [dbo].[User] ([Username], [Password], [FullName], [DOB], [Sex], [Email], [PhoneNumber], [Address], [Role], [Wallet]) VALUES (N'lethihoa321', N'7eb5de9cb4b68f4bde9ebe4c1ccf01a658eb0d70f62f2f9512ea6ef258781a2c', N'Lê Thị Hoa', CAST(N'1982-12-25' AS Date), N'Female', N'lethihoa321@gmail.com', N'0781781305', N'Long An', N'User', CAST(774999810.00 AS Decimal(18, 2)))
GO
INSERT [dbo].[User] ([Username], [Password], [FullName], [DOB], [Sex], [Email], [PhoneNumber], [Address], [Role], [Wallet]) VALUES (N'luongvanlam654', N'630bf6112e079d6d914eb72c1601ee8f1503d6b50214e72fec163aceee4cfc92', N'Lương Văn Lâm', CAST(N'1998-04-01' AS Date), N'Male', N'luongvanlam654@gmail.com', N'0968196570', N'Quảng Bình', N'User', CAST(803592010.00 AS Decimal(18, 2)))
GO
INSERT [dbo].[User] ([Username], [Password], [FullName], [DOB], [Sex], [Email], [PhoneNumber], [Address], [Role], [Wallet]) VALUES (N'lythimy123', N'ef8c9d7c7629102f798222abf61655155efd81d0a1c478ca23f8a3b650d42b9d', N'Lý Thị Mỹ', CAST(N'1990-01-01' AS Date), N'Female', N'lythimy654@gmail.com', N'0585423100', N'Hà Tĩnh', N'User', CAST(153104009.00 AS Decimal(18, 2)))
GO
INSERT [dbo].[User] ([Username], [Password], [FullName], [DOB], [Sex], [Email], [PhoneNumber], [Address], [Role], [Wallet]) VALUES (N'macthihue456', N'8c39e9b3e0ae0f4227d40d4caee1e611622ede60f250b7824b7d0d910a26f608', N'Mạc Thị Huệ', CAST(N'1992-01-30' AS Date), N'Female', N'macthihue456@gmail.com', N'0838819675', N'Bình Thuận', N'User', CAST(2000000001.00 AS Decimal(18, 2)))
GO
INSERT [dbo].[User] ([Username], [Password], [FullName], [DOB], [Sex], [Email], [PhoneNumber], [Address], [Role], [Wallet]) VALUES (N'maivantam012', N'88c67adee9d331a58b13d2d7179c618961347f99530cf55bad754020a7f73632', N'Mai Văn Tâm', CAST(N'1993-12-27' AS Date), N'Male', N'maivantam012@gmail.com', N'0990221972', N'Tiền Giang', N'User', CAST(723114000.00 AS Decimal(18, 2)))
GO
INSERT [dbo].[User] ([Username], [Password], [FullName], [DOB], [Sex], [Email], [PhoneNumber], [Address], [Role], [Wallet]) VALUES (N'minecrafter', N'9970626666560a32465d4ce10d28f3233365af833e15eed59884d9477862c379', N'Mai Hoàng Ân', CAST(N'2004-06-05' AS Date), N'Male', N'anmhce180552@fpt.edu.vn', N'0353480984', N'Sóc Trăng', N'Admin', CAST(554284525.00 AS Decimal(18, 2)))
GO
INSERT [dbo].[User] ([Username], [Password], [FullName], [DOB], [Sex], [Email], [PhoneNumber], [Address], [Role], [Wallet]) VALUES (N'nguyenvanloc654', N'663539dd74f15752fbc21c97af6bfab147c8aeae6c65f9a8d34c196af5d5c934', N'Nguyễn Văn Lộc', CAST(N'1997-08-13' AS Date), N'Male', N'nguyenvanloc654@gmail.com', N'0927952529', N'Lào Cai', N'User', CAST(70539230.00 AS Decimal(18, 2)))
GO
INSERT [dbo].[User] ([Username], [Password], [FullName], [DOB], [Sex], [Email], [PhoneNumber], [Address], [Role], [Wallet]) VALUES (N'phanvanduong', N'7d77eef7229a4473c52aa7487fd9e5427e86bf37e0cf44dd49cec1cdde971de0', N'Phan Văn Dương', CAST(N'1988-12-10' AS Date), N'Male', N'phanvanduong789@gmail.com', N'0941241488', N'Hải Dương', N'User', CAST(11701891.00 AS Decimal(18, 2)))
GO
INSERT [dbo].[User] ([Username], [Password], [FullName], [DOB], [Sex], [Email], [PhoneNumber], [Address], [Role], [Wallet]) VALUES (N'princess123', N'7dc8e1ac1994e90391f6f5a35b7eec5f55655f0a2229a5c1678a0f67cab653f5', N'Lê Khắc Huy', CAST(N'2004-09-28' AS Date), N'Male', N'huylkce180311@fpt.edu.vn', N'0976840745', N'Cần Thơ', N'Admin', CAST(9858453525.00 AS Decimal(18, 2)))
GO
INSERT [dbo].[User] ([Username], [Password], [FullName], [DOB], [Sex], [Email], [PhoneNumber], [Address], [Role], [Wallet]) VALUES (N'tomandmeow', N'404cdd7bc109c432f8cc2443b45bcfe95980f5107215c645236e577929ac3e52', N'Nguyễn Tấn Minh', CAST(N'2004-03-03' AS Date), N'Male', N'minhntce180111@fpt.edu.vn', N'0976867579', N'Vĩnh Long', N'Admin', CAST(564257661.00 AS Decimal(18, 2)))
GO
INSERT [dbo].[User] ([Username], [Password], [FullName], [DOB], [Sex], [Email], [PhoneNumber], [Address], [Role], [Wallet]) VALUES (N'truongthilinh789', N'8376fdf76cf93d22fd8f99a46121665562dd2d8306104cac9290f0cfeb469c0b', N'Trương Thị Linh', CAST(N'1994-09-08' AS Date), N'Female', N'truongthilinh789@gmail.com', N'0825213303', N'Nghệ An', N'User', CAST(671222000.00 AS Decimal(18, 2)))
GO
INSERT [dbo].[User] ([Username], [Password], [FullName], [DOB], [Sex], [Email], [PhoneNumber], [Address], [Role], [Wallet]) VALUES (N'vovanson123', N'c82de2c6c54792fda9ceebd820134c70aa316a153dcb797bd72c6df59dd8c7c7', N'Võ Văn Sơn', CAST(N'1984-02-29' AS Date), N'Male', N'vovanson123@gmail.com', N'0998586879', N'Quảng Ngãi', N'User', CAST(43997373.00 AS Decimal(18, 2)))
GO
INSERT [dbo].[User] ([Username], [Password], [FullName], [DOB], [Sex], [Email], [PhoneNumber], [Address], [Role], [Wallet]) VALUES (N'vuthinga789', N'33e16fa352e56454321d6708fdf186e04483d63c56d9b810c23f0d47eece4c76', N'Vũ Thị Nga', CAST(N'1992-07-20' AS Date), N'Female', N'vuthinga654@gmail.com', N'0826747805', N'Bình Dương', N'User', CAST(738530338.00 AS Decimal(18, 2)))
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__User__A9D105346D66C7F0]    Script Date: 7/14/2024 5:52:09 PM ******/
ALTER TABLE [dbo].[User] ADD UNIQUE NONCLUSTERED 
(
	[Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Order] ADD  DEFAULT (getdate()) FOR [OrderDate]
GO
ALTER TABLE [dbo].[User] ADD  DEFAULT ('User') FOR [Role]
GO
ALTER TABLE [dbo].[User] ADD  DEFAULT ((0.00)) FOR [Wallet]
GO
ALTER TABLE [dbo].[Order]  WITH CHECK ADD FOREIGN KEY([Username])
REFERENCES [dbo].[User] ([Username])
GO
ALTER TABLE [dbo].[OrderDetail]  WITH CHECK ADD FOREIGN KEY([OrderID])
REFERENCES [dbo].[Order] ([OrderID])
GO
ALTER TABLE [dbo].[OrderDetail]  WITH CHECK ADD FOREIGN KEY([ProductID])
REFERENCES [dbo].[Product] ([ProductID])
GO
ALTER TABLE [dbo].[Product]  WITH CHECK ADD FOREIGN KEY([BrandID])
REFERENCES [dbo].[Brand] ([BrandID])
GO
ALTER TABLE [dbo].[Product]  WITH CHECK ADD FOREIGN KEY([CategoryID])
REFERENCES [dbo].[Category] ([CategoryID])
GO
USE [master]
GO
ALTER DATABASE [BeluStyle] SET  READ_WRITE 
GO
