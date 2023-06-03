-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 03, 2023 at 09:21 PM
-- Server version: 10.4.25-MariaDB
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `java_erp`
--

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `status` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `name`, `status`) VALUES
(1, 'drinks', 'Active'),
(2, 'sandwitchs', 'Active'),
(3, 'snaks', 'Active'),
(8, 'soda', 'Active'),
(9, 'juces', 'Active');

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `phone` text NOT NULL,
  `debit_status` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`id`, `name`, `phone`, `debit_status`) VALUES
(1, 'mohamed ali', '01216546546', 20),
(5, 'talaat', '020202121', 545544),
(11, 'ali hassan', '324234', 23),
(14, 'محمد علي', '01216546546', 20),
(15, 'أحمد حسن', '234234', 3),
(16, 'إبراهيم محمد', '234234234', 50);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `price` double NOT NULL,
  `status` varchar(15) NOT NULL,
  `qti` int(11) NOT NULL,
  `category_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `name`, `price`, `status`, `qti`, `category_id`) VALUES
(1, 'coca', 3, 'Active', 50, 1),
(2, 'فول', 15000, 'Active', 40, 2),
(3, 'مهلبية', 10, 'Active', 2, 1),
(4, 'طعمية', 4, 'Active', 50, 2),
(5, 'fanta', 20, 'Active', 50, 8);

-- --------------------------------------------------------

--
-- Table structure for table `sales_invoices`
--

CREATE TABLE `sales_invoices` (
  `id` bigint(20) NOT NULL,
  `invoice_id` bigint(20) NOT NULL,
  `date` date NOT NULL,
  `customer_id` bigint(20) NOT NULL,
  `total` double NOT NULL,
  `paid` double NOT NULL,
  `remain` double NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `sales_invoices`
--

INSERT INTO `sales_invoices` (`id`, `invoice_id`, `date`, `customer_id`, `total`, `paid`, `remain`, `status`) VALUES
(1, 1, '2023-05-01', 1, 40, 40, 0, 'closed'),
(2, 2, '2023-05-01', 1, 50.9, 30, 20.9, 'closed'),
(3, 3, '2023-06-01', 1, 50.9, 40, 10, 'closed'),
(4, 4, '2023-06-01', 14, 50.9, 130, 0, 'closed'),
(5, 5, '2023-06-01', 1, 50.9, 1040, 1, 'closed'),
(6, 6, '2023-06-01', 5, 28, 28, 0, 'closed'),
(7, 7, '2023-07-01', 1, 15, 15, 0, 'closed'),
(8, 8, '2023-07-01', 5, 14, 14, 2, 'closed'),
(9, 9, '2023-07-01', 11, 14, 14, 0, 'closed'),
(10, 10, '2023-06-03', 11, 5, 5, 0, 'closed'),
(11, 11, '2023-06-04', 11, 27, 27, 0, 'closed');

-- --------------------------------------------------------

--
-- Table structure for table `sales_invoice_details`
--

CREATE TABLE `sales_invoice_details` (
  `id` bigint(20) NOT NULL,
  `sales_invoice_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `qti` int(11) NOT NULL,
  `price` double NOT NULL,
  `total` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `sales_invoice_details`
--

INSERT INTO `sales_invoice_details` (`id`, `sales_invoice_id`, `product_id`, `qti`, `price`, `total`) VALUES
(3, 3, 1, 2, 20, 40),
(4, 3, 1, 2, 15, 30),
(5, 3, 2, 1, 10, 10),
(6, 4, 3, 1, 50, 50),
(7, 4, 2, 1, 50, 50),
(8, 4, 4, 1, 30, 30),
(9, 5, 3, 3, 10, 30),
(10, 5, 3, 50, 10, 500),
(11, 5, 3, 50, 10, 500),
(12, 5, 1, 3, 3, 9),
(13, 6, 1, 2, 3, 6),
(14, 6, 3, 1, 10, 10),
(15, 6, 2, 1, 12, 12),
(16, 7, 1, 1, 3, 3),
(17, 7, 2, 1, 12, 12),
(18, 8, 4, 2, 4, 8),
(19, 8, 1, 2, 3, 6),
(20, 9, 4, 1, 4, 4),
(21, 9, 3, 1, 10, 10),
(22, 10, 1, 1, 3, 3),
(23, 10, 4, 1, 2, 2),
(24, 11, 4, 1, 4, 4),
(25, 11, 5, 1, 20, 20),
(26, 11, 1, 1, 3, 3),
(27, 12, 1, 1, 3, 3),
(28, 12, 4, 1, 5, 5);

-- --------------------------------------------------------

--
-- Table structure for table `suppliers`
--

CREATE TABLE `suppliers` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `phone` text NOT NULL,
  `address` text NOT NULL,
  `debit_status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `suppliers`
--

INSERT INTO `suppliers` (`id`, `name`, `phone`, `address`, `debit_status`) VALUES
(5, 'asd', '01259548855', 'sdfsdf', 3),
(6, 'qqq', '234234', 'sdfsdf', 3),
(7, 'www', '234234', 'sdfsdf', 3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sales_invoices`
--
ALTER TABLE `sales_invoices`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sales_invoice_details`
--
ALTER TABLE `sales_invoice_details`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `suppliers`
--
ALTER TABLE `suppliers`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `sales_invoices`
--
ALTER TABLE `sales_invoices`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `sales_invoice_details`
--
ALTER TABLE `sales_invoice_details`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT for table `suppliers`
--
ALTER TABLE `suppliers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
