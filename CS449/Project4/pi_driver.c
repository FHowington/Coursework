/*
 * Forbes Turley 8/4/17
 * CS 449 Project 4
 * University of Pittsburgh
 * This file is the driver module that should be
 * dynamically loaded to allow /dev/pi to be used
 */

#include <linux/fs.h>
#include <linux/init.h>
#include <linux/miscdevice.h>
#include <linux/module.h>
#include "pi.h"
#include <asm/uaccess.h>

/*
 * pi_read if the function called when a read() is made to 
 * /dev/pi. It returns the digits of pi up to and including 
 * the specified length in count
 */

static ssize_t pi_read(struct file * file, char * buf, 
		size_t count, loff_t *ppos)
{
	char *pi_str;
	char *result;
	int total_size;
	int pos = (int)*ppos;

	/*
	 * The total size is calculated based on the total of the
	 * number of digits and the digit at which the count should start
	 * This is because pi is generated as a stream and we must generate
	 * from 0 up to the desired index
	 */

	total_size = pos + count;
	total_size = total_size + total_size%4;

	pi_str = kmalloc(total_size, GFP_KERNEL);
	result = kmalloc(count, GFP_KERNEL);
	pi(pi_str, total_size);

	/*
	 * After the large pi string has been created
	 * it is cut into a substring which is then
	 * passed back to the user
	 */
	memcpy(result, &pi_str[pos], count);

	/*
	 * Besides copying the string to the user provided buffer,
	 * this function also checks that the user has permission to
	 * write to the buffer, that it is mapped, etc.
	 */

	if (copy_to_user(buf, result, count))
		return -EINVAL;
	/*
	 * Tell the user how much data we wrote.
	 */

	*ppos = count;

	return count;
}

/*
 * The only file operation we care about is read.
 */

static const struct file_operations pi_fops = {
	.owner		= THIS_MODULE,
	.read		= pi_read,
};

static struct miscdevice pi_dev = {
	/*
	 * We don't care what minor number we end up with, so tell the
	 * kernel to just pick one.
	 */
	MISC_DYNAMIC_MINOR,
	/*
	 * Name ourselves /dev/pi
	 */
	"pi",
	/*
	 * What functions to call when a program performs file
	 * operations on the device.
	 */
	&pi_fops
};

	static int __init
pi_init(void)
{
	int ret;

	/*
	 * Create the "pi" device in the /sys/class/misc directory.
	 * Udev will automatically create the /dev/pi device using
	 * the default rules.
	 */
	ret = misc_register(&pi_dev);
	if (ret)
		printk(KERN_ERR
				"Unable to register \"Pi Digits\" misc device\n");

	return ret;
}

module_init(pi_init);

	static void __exit
pi_exit(void)
{
	misc_deregister(&pi_dev);
}

module_exit(pi_exit);

MODULE_LICENSE("GPL");
MODULE_AUTHOR("Forbes Turley, <fot3@pitt.edu>");
MODULE_DESCRIPTION("\"Pi driver\" minimal module");
MODULE_VERSION("dev");
